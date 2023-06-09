package com.eun.tutorial.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.eun.tutorial.service.UserService;
import com.eun.tutorial.service.user.PrincipalDetails;
import com.eun.tutorial.util.AuthUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    
    private final SessionRegistry sessionRegistry;
    private final UserService userService;
    
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        List<SessionInformation> sessions = sessionRegistry.getAllSessions(AuthUtils.getPrincipalDetails(), true);
        userService.updateLastLoginDt(AuthUtils.getLoginUser(), "");
        
        for (SessionInformation session : sessions) {
            session.expireNow();
        }
    }
}