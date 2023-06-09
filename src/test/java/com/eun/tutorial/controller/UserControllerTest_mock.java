package com.eun.tutorial.controller;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import com.eun.tutorial.dto.UserInfoDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest_mock {

    @InjectMocks
    private UserController userController;

    @Mock
    private SessionRegistry sessionRegistry;

    @Test
    public void testForceLogout() {
        // 사용자 정보 설정
        String username = "testUser";
        String password = "testPassword";
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(username);
        userInfoDTO.setPassword(password);

        // 인증 토큰 설정
        String token = "your-auth-token";
        String authToken = "Bearer " + token;

        // 목 객체 설정
        SessionInformation sessionInformation = Mockito.mock(SessionInformation.class);
        List<SessionInformation> sessions = Collections.singletonList(sessionInformation);
        Mockito.when(sessionRegistry.getAllSessions(username, true)).thenReturn(sessions);

        // 요청 헤더 및 본문 데이터 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        HttpEntity<UserInfoDTO> entity = new HttpEntity<>(userInfoDTO, headers);

        // 테스트 수행
        userController.forceLogout(token, userInfoDTO);

        // 세션 만료 확인
        Mockito.verify(sessionInformation).expireNow();
    }
}
