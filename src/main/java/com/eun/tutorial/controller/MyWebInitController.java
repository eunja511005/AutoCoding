package com.eun.tutorial.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.service.UserService;
import com.eun.tutorial.service.ZthhFileAttachService;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@Transactional
public class MyWebInitController {
	
	private static final Logger logger = LoggerFactory.getLogger(MyWebInitController.class);
	private final UserService userService;
	private final ZthhFileAttachService zthhFileAttachService;
	
    @Value("${spring.servlet.multipart.location}")
    private String multiPathPath;
	
	@Autowired private BCryptPasswordEncoder passwordEncoder; // 시큐리티에서 빈(Bean) 생성할 예정
	
	@Autowired private LogoutHandler logoutHandler;
	
	@GetMapping("/")
	public ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/common/main");
		return modelAndView;
	}	
	
	@GetMapping("/signinInit")
    public ModelAndView signinInit(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("request url : /signinInit");
		
		// 1. 일단 로그아웃 버튼 눌렀을때 처럼 세션 정리
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutHandler.logout(request, response, auth);
        }
		
        // 2. 로그인 페이지로 이동
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("signin");
        modelAndView.setViewName("jsp/main/content/signin");

        return modelAndView;
    }
	
    @GetMapping("/login-status")
    public @ResponseBody Map<String, Object> getLoginStatus(Authentication authentication) {
    	Map<String, Object> res = new HashMap<>();
        if(authentication == null) {
        	res.put("result", "not login");
        } else {
        	res.put("result", "login");
        	
        	//user picture setting
        	log.debug("user picture setting");
        	
        	
//        	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal()!=null){
//                if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomOAuth2User){
//                	CustomOAuth2User customOAuth2User = (CustomOAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                    res.put("userProfileImg", customOAuth2User.getPicture());
//                	res.put("userName", customOAuth2User.getName());
//                }else {
//                	UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
//                	res.put("userProfileImg", userDetailsImpl.getPicture());
//                	res.put("userName", userDetailsImpl.getUsername());
//                }
//            }
        	
        	PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        	res.put("userProfileImg", principalDetails.getPicture());
        	res.put("userName", principalDetails.getUsername());

        	return res;
        	
        }
        return res;
    }
	
	@GetMapping("/joinInit")
    public ModelAndView joinInit() {
		logger.debug("request url : /joinInit");
		
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("join4");

        return modelAndView;
    }
	
    @PostMapping("/join")
    public @ResponseBody Map<String, Object> join(MultipartHttpServletRequest multipartFiles, 
    		UserInfoDTO userInfoDTO) throws IOException {
    	
    	logger.debug("request url : /join");
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	Iterator<String> fileNames = multipartFiles.getFileNames();
        String fileName = "";
        String mediaTypeString = "";
        int seq = 0;
        while (fileNames.hasNext()) {
                fileName = fileNames.next();
                log.info("requestFile {} ", fileName);
                List<MultipartFile> multipartFilesList = multipartFiles.getFiles(fileName);
                UUID uuid = UUID.randomUUID();
                String attachId = "user_attach_"+uuid;
                for (MultipartFile multipartFile : multipartFilesList) {
                	seq++;
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    String current_date = now.format(dateTimeFormatter);

                    String originalFileExtension;
                    String contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                    	res.put("result", "Could not upload the file: " + multipartFile.getOriginalFilename() + "!");
                    	return res;
                    } else {
                    	String mimeType = new Tika().detect(multipartFile.getInputStream()); //where 'file' is a File object or InputStream of the uploaded file
                    	MediaType mediaType = MediaType.parse(mimeType);
                    	mediaTypeString = mediaType.getType() + "/" + mediaType.getSubtype();

                    	if(!mediaTypeString.equals("image/jpeg") && !mediaTypeString.equals("image/png") && !mediaTypeString.equals("image/gif")) {
                    		res.put("result", "You can upload file's media type of image/jpeg, image/png");
                    		return res;
                    	}
                        log.info("tikaMimeType {} : "+mimeType);
                        originalFileExtension = MimeTypeUtils.parseMimeType(mimeType).getSubtype();
                        originalFileExtension = "."+originalFileExtension;
                        log.info("originalFileExtension {} : "+originalFileExtension);
                    }
                    String new_file_name = current_date + "/" + System.nanoTime() + originalFileExtension;
                    File newFile = new File(multiPathPath + new_file_name);
                    if (!newFile.exists()) {
                        boolean wasSuccessful = newFile.mkdirs();
                    }

                    multipartFile.transferTo(newFile);

                    log.info("Uploaded the file successfully: " + multipartFile.getOriginalFilename());
                    log.info("new file name: " + new_file_name);
                    userInfoDTO.setPicture("/"+new_file_name);
                    
                    ZthhFileAttachDTO zthhFileAttachDTO = ZthhFileAttachDTO.builder()
    									                				.attachId(attachId)
    									                				.sequence(seq)
    									                				.originalFileName(multipartFile.getOriginalFilename())
    									                				.fileName(new_file_name)
    									                				.fileType(mediaTypeString)
    									                				.fileSize(multipartFile.getSize())
    									                				.filePath(multiPathPath + new_file_name)
    									                				.createId(userInfoDTO.getUsername())
    									                				.updateId(userInfoDTO.getUsername())
    									                				.build();
                    				
                    zthhFileAttachService.save(zthhFileAttachDTO);
				}
    	
        }
    	
        // 임시 파일 지우기
        File dir = new File(multiPathPath);
        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".tmp")) {
                file.delete();
            }
        }
        
		userService.addUser(userInfoDTO);
		
		
        res.put("result", "registe success");
        res.put("redirectUrl", "/main");
		return res;
	}
    
    // 자동 로그아웃 안 될때 사용
    @GetMapping("/signout")
    public ModelAndView performLogout(HttpServletRequest request, HttpServletResponse response) {
        // 1. 세션 삭제
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutHandler.logout(request, response, auth);
        }
        
        // 2. 로그인 페이지로 이동
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsp/main/common/main");

        return modelAndView;
    }
}
