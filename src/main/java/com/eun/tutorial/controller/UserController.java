package com.eun.tutorial.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.UserManageDTO;
import com.eun.tutorial.dto.project.ProjectListRequest;
import com.eun.tutorial.dto.project.ProjectListResponse;
import com.eun.tutorial.mapper.UserMapper;
import com.eun.tutorial.service.project.ProjectService;
import com.eun.tutorial.service.user.PrincipalDetails;
import com.eun.tutorial.util.FileUtil;
import com.eun.tutorial.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final SessionRegistry sessionRegistry;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final FileUtil fileUtil;
    private final ProjectService projectService;
    
    @GetMapping("/active-sessions")
    public List<String> getActiveSessions() {
        List<String> activeUsers = sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(Object::toString)
                .collect(Collectors.toList());
        
        return activeUsers;
    }
    
    @PostMapping("/force-logout")
    public void forceLogout(@RequestHeader("Authorization") String token, @RequestBody UserInfoDTO userInfoDTO) {
    	String authToken = token.substring(7); // "Bearer " 이후의 토큰 부분 추출
    	
    	if (JwtTokenUtil.validateToken(authToken, userInfoDTO)) {
            // 토큰이 유효한 경우 데이터 반환
    		for(Object principalDetails: sessionRegistry.getAllPrincipals()) {
    			if(principalDetails instanceof PrincipalDetails) {
    				log.info(((PrincipalDetails)principalDetails).getName());
    				log.info(((PrincipalDetails)principalDetails).getSessionId());
    			}
    			
    		}
//        	List<SessionInformation> sessions = sessionRegistry.getAllSessions(userInfoDTO.getUsername(), true);
        	List<SessionInformation> sessions = sessionRegistry.getAllSessions(new PrincipalDetails(userInfoDTO), true);
            
            for (SessionInformation session : sessions) {
                session.expireNow();
            }
        } else {
            // 토큰이 유효하지 않은 경우 예외 처리
            throw new IllegalArgumentException("Invalid token");
        }

    }
    
    @PostMapping("/getToken")
    public ResponseEntity<String> getToken(@RequestBody UserInfoDTO userInfoDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", userInfoDTO.getUsername()); // 가져온 데이터에 키와 값을 지정

        UserInfoDTO user = userMapper.getUser(map);
        if (user != null && passwordEncoder.matches(userInfoDTO.getPassword(), user.getPassword())) {
            // 인증 성공 시 JWT 토큰 생성
            String token = JwtTokenUtil.generateToken(userInfoDTO);
            return ResponseEntity.ok(token);
        } else {
            // 인증 실패 시 에러 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
	@PostMapping("/mobile/image/save")
	public @ResponseBody ApiResponse saveUserManage(@RequestParam("file") MultipartFile file, UserManageDTO userManageDTO) throws IOException {
		userManageDTO.setPicture(fileUtil.saveImageWithOriginName(file, "openImg/mobile"));
		//userManageService.mergeUser(userManageDTO);
		return new ApiResponse<>(true, "Success save", null);
	}
	
    @PostMapping("/project/list")
    public @ResponseBody ProjectListResponse getProjectList(@RequestBody ProjectListRequest projectListRequest) {
        return projectService.getProjects(projectListRequest);
    }
    
}