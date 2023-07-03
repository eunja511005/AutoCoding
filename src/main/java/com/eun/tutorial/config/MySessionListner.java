package com.eun.tutorial.config;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import com.eun.tutorial.service.UserService;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySessionListner implements HttpSessionListener{

    private SessionRegistry sessionRegistry;
    private UserService userService;

    public MySessionListner(SessionRegistry sessionRegistry, UserService userService) {
        this.sessionRegistry = sessionRegistry;
        this.userService = userService;
    }

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.info("session destroyed");
		
        HttpSession session = se.getSession();
        String sessionId = session.getId();
        
        // 세션 정보 가져오기
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if (sessionInformation != null) {
            // 세션 만료 처리
            sessionInformation.expireNow();

            // 마지막 로그인 날짜 업데이트
            Object principal = sessionInformation.getPrincipal();
            if (principal instanceof PrincipalDetails) {
                PrincipalDetails principalDetails = (PrincipalDetails) principal;
                String username = principalDetails.getUsername();
                userService.updateLastLoginDt(username, null);
            }
        }
       
    }
}
