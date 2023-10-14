package com.eun.tutorial.controller;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.mapper.UserMapper;
import com.eun.tutorial.service.UserService;
import com.eun.tutorial.service.ZthhFileAttachService;
import com.eun.tutorial.service.user.PrincipalDetails;
import com.eun.tutorial.util.AuthUtils;
import com.eun.tutorial.util.EncryptionUtils;

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
    private final SessionRegistry sessionRegistry;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final LogoutHandler logoutHandler;
    private final EncryptionUtils encryptionUtils;
    
    @Value("${spring.servlet.multipart.location}")
    private String multiPathPath;
	
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
        modelAndView.addObject("publicKey", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5HGkxvPg1NVA4Rb5wsyPnQs+6AmMIGeFLE4edLGjWuvWZVuck6tIZCmMaWs6GWho/vcyTO/lC4UaRUm3egIE5duHytVZPe14kpSWyV9ilmUEMu/5SqLFlCpxGp/gOel1+aE6/fr1sjxoMUGvZuiQe222el1O/SSukhMIRnGDHBQTMoKwd0imv7keMyxKCGKfMJD9VEc4EbZ8e/at3chPZhKmJSjdAGQc3sGAo384vdpBNdTr/YHm8rQ/82fUDoxouvS7xlenDdRRgFrdmxWC2E24L8n9vSyU4zjRvvRlJ/NNW08xpCCWdEk8RU4sNCbW/v7Q8doDB4XAD92i35l4UQIDAQAB");
        modelAndView.setViewName("join4");

        return modelAndView;
    }
	
	@GetMapping("/recoveryPassword")
	public ModelAndView recoveryPasswordInit() {
		logger.debug("request url : /recoveryPassword");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("publicKey", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5HGkxvPg1NVA4Rb5wsyPnQs+6AmMIGeFLE4edLGjWuvWZVuck6tIZCmMaWs6GWho/vcyTO/lC4UaRUm3egIE5duHytVZPe14kpSWyV9ilmUEMu/5SqLFlCpxGp/gOel1+aE6/fr1sjxoMUGvZuiQe222el1O/SSukhMIRnGDHBQTMoKwd0imv7keMyxKCGKfMJD9VEc4EbZ8e/at3chPZhKmJSjdAGQc3sGAo384vdpBNdTr/YHm8rQ/82fUDoxouvS7xlenDdRRgFrdmxWC2E24L8n9vSyU4zjRvvRlJ/NNW08xpCCWdEk8RU4sNCbW/v7Q8doDB4XAD92i35l4UQIDAQAB");
		modelAndView.setViewName("recoveryPassword");
		
		return modelAndView;
	}
	
	@PostMapping("/recoveryPassword")
	public @ResponseBody ApiResponse<String> recoveryPassword(@RequestBody UserInfoDTO userInfoDTO) throws CustomException {
		logger.debug("request url : /recoveryPassword");
    	
		try {
			return userService.recoveryPassword(userInfoDTO);
        } catch (Exception e) {
        	throw new CustomException(ErrorCode.Fail_SEND_EMAIL, e);
        }
        
    }
	
	@GetMapping("/resetPassword")
	public ModelAndView resetPasswordInit() {
		logger.debug("request url : /resetPassword");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("publicKey", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5HGkxvPg1NVA4Rb5wsyPnQs+6AmMIGeFLE4edLGjWuvWZVuck6tIZCmMaWs6GWho/vcyTO/lC4UaRUm3egIE5duHytVZPe14kpSWyV9ilmUEMu/5SqLFlCpxGp/gOel1+aE6/fr1sjxoMUGvZuiQe222el1O/SSukhMIRnGDHBQTMoKwd0imv7keMyxKCGKfMJD9VEc4EbZ8e/at3chPZhKmJSjdAGQc3sGAo384vdpBNdTr/YHm8rQ/82fUDoxouvS7xlenDdRRgFrdmxWC2E24L8n9vSyU4zjRvvRlJ/NNW08xpCCWdEk8RU4sNCbW/v7Q8doDB4XAD92i35l4UQIDAQAB");
		modelAndView.setViewName("resetPassword");
		
		return modelAndView;
	}
	
	@PostMapping("/resetPassword")
	public @ResponseBody ApiResponse<String> resetPassword(@RequestBody UserInfoDTO userInfoDTO) throws CustomException {
		logger.debug("request url : /resetPassword");
    	
		try {
			return userService.resetPassword(userInfoDTO);
        } catch (Exception e) {
        	throw new CustomException(ErrorCode.Fail_SEND_EMAIL, e);
        }
        
    }
	
	
    @PostMapping("/join")
    public @ResponseBody Map<String, Object> join(MultipartHttpServletRequest multipartFiles, 
    		UserInfoDTO userInfoDTO) throws IOException, GeneralSecurityException {
    	
    	logger.debug("request url : /join");
    	
    	//민감 정보(패스워드) 복호화
    	String encryptedDataStr = userInfoDTO.getPassword();

		// 비밀키를 사용하여 암호화된 데이터를 복호화 (AES-CBC 알고리즘 사용)
    	String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDkcaTG8+DU1UDhFvnCzI+dCz7oCYwgZ4UsTh50saNa69ZlW5yTq0hkKYxpazoZaGj+9zJM7+ULhRpFSbd6AgTl24fK1Vk97XiSlJbJX2KWZQQy7/lKosWUKnEan+A56XX5oTr9+vWyPGgxQa9m6JB7bbZ6XU79JK6SEwhGcYMcFBMygrB3SKa/uR4zLEoIYp8wkP1URzgRtnx79q3dyE9mEqYlKN0AZBzewYCjfzi92kE11Ov9gebytD/zZ9QOjGi69LvGV6cN1FGAWt2bFYLYTbgvyf29LJTjONG+9GUn801bTzGkIJZ0STxFTiw0Jtb+/tDx2gMHhcAP3aLfmXhRAgMBAAECggEAT7Re+5OHtHqbYm2zwwXAbdjIoAkEvSGhU24GLkz0Y/q5GTA3l0ZBwcDFXtqssMS/LYZuJG3nCnfsTJSF0an2r4RDAsAhnPvH/8ycN7JyIWspZeRYpHPaX/HW+KUjhNEx+mEIxijTpNZyvAzg+BTYpFgJPPRlZOlxh0Hgt0NPrXGiv5AGsZKHeMNR6ZmgIGRT38BwQat4e3azvqNGV89axfuWOktJByyWYT4A9P/Euse3zK93p1Q2vyATwBgnX3CT64xdShaUXaxuBmezoWF3gEXNoh2rVkDsD2IKZG1AF+t6CLJomeZwEfJnZYPtX5z42EUJobTw9AZWCXsZ5g3wiQKBgQDniOOaifJ88H6finbhaVksC10aMTxa57DBkPhCC7GKeWt651cbQBu68zoACi4SSgaBT302k/pEw1BAqslmAJkjJrdW4SP6gYJUzNyIkENHw86t5b8+fQqqhHdgHsvx5Mdr9eVlvyoyX/gZKN/tHgoD/VZdEgSpEP57DEW/DiDcvwKBgQD8lSWnlk9pbmCNbU9DNIQsdHyi9k7Asb7NpPy1XS/uOLCdniFA7b3OvmgQDIMhc/qXkYhRAChhR7LIT6DgY566tOfT1tm5FZ1tRn7eg0vwrTZ+TLEko6i1L/Tm0QrSGVOrnDjm0+N+NKrG54zHOxOpO5XvpS8iG7S5KO3m5TMe7wKBgQC3wKBCCbD1DzivDYkDpEQs/GfLXb/0tWRGevNMF1Oz/mEajXdIHTzkxhwF5A5kXXOENL2/DvnUkN2kNObZmSfwCc3/mXagXSA+hMeRaky7K99fi7KXuU05vx+unUJmm5bZS7HfajPm/ts7vIDbArgYKnrcKmygcOhGZ5sC4geaqQKBgQDMKgrL0fXStQOajdbZ7eNAw8/TMeEqZQJj247hUrfhiTVJ0n3yq7kXGlWnU1XTfpn6Vgqn3sbFC116CNNzTVMKfBw/4ZUPxGcB40+9sMd7fadko700bo16F4+P2z0x4oL9XkOoYXGrnArGyHfEuv4Dd0SU9yKIIXkNigXy8yVFRQKBgE3ooHkxRnuyG8zzXOGNdhyRFL41NBMdSQaWMVhO8g8WBW3apVbyytknc0JAFUnxEsJjsgLIugHx+rtr1wmsYzHXpSH6coS3jFR/iAStkSrCcNuB+FQi70vkrtUcptmWkRXU29SACVZhApZskLBcFZUXDpTaAKYz+M60Y04v+0we";
        String decryptedData = encryptionUtils.decryptWithPrivateKey(encryptedDataStr, privateKey);

        logger.info("decryptedData : {}", decryptedData);
        
        userInfoDTO.setPassword(decryptedData);
    	
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
    
    @PostMapping("/signout2")
    public @ResponseBody Map<String, Boolean> performLogout2(@RequestBody Map<String, Object> reqMap) {
    	Map<String, Boolean> resMap = new HashMap<>();
    	
    	Map<String, Object> map = new HashMap<>();
        map.put("username", reqMap.get("username")); 
        UserInfoDTO user = userMapper.getUser(map);
        
        if (user != null && passwordEncoder.matches((String)reqMap.get("password"), user.getPassword())) {
        	// 인증 성공시 세션 삭제
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(new PrincipalDetails(user), true);
            userService.updateLastLoginDt(AuthUtils.getLoginUser(), "");
            
            for (SessionInformation session : sessions) {
                session.expireNow();
            }
            
            resMap.put("success", true);
            return resMap;
        } else {
            resMap.put("success", false);
            return resMap;
        }
    }
    
    // 자동 로그아웃 안 될때 사용
    @GetMapping("/sessionExpire")
    public ModelAndView sessionExpire(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jsp/error/sessionExpire");

        return modelAndView;
    }
    
}
