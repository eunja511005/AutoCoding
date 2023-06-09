package com.eun.tutorial.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.mapper.UserMapper;
import com.eun.tutorial.service.user.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserMapper userMapper;
    
    @Test
    void testGetActiveSessions() {
        // 더미 사용자 세션 생성
        createUserSession("user1");
        createUserSession("testUser");
        
        // 인증 토큰 생성
        String token = "your-auth-token";

        // 테스트 요청 헤더에 인증 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // /active-sessions 엔드포인트 호출
        ResponseEntity<List> response = restTemplate.exchange("/api/active-sessions", HttpMethod.GET, entity, List.class);
        List<String> activeSessions = response.getBody();

        // 사용자 세션 수와 응답 데이터 수가 일치하는지 확인
        assertEquals(sessionRegistry.getAllPrincipals().size(), activeSessions.size());
        
        // 사용자 세션 아이디와 사용자 이름 비교
        for (String sessionId : activeSessions) {
            List<Object> principals = sessionRegistry.getAllPrincipals();
            for (Object principal : principals) {
                if (principal instanceof PrincipalDetails) {
                	PrincipalDetails userDetails = (PrincipalDetails) principal;
                    if (sessionId.equals(userDetails.getSessionId())) {
                        String username = userDetails.getUsername();
                        // 비교 작업 수행
                        // ...
                    }
                }
            }
        }
    }

    
    @Test
    void testForceLogout() {
        // 더미 사용자 세션 생성
        createUserSession("testUser");
        
        // 사용자 정보 설정
        String username = "testUser";
        String password = "password";
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(username);
        userInfoDTO.setPassword(password);
        
        // 인증 토큰 생성
        String token = getToken(username, password);

        // 테스트 요청 헤더에 인증 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<UserInfoDTO> entity = new HttpEntity<>(userInfoDTO, headers);
        
        // /force-logout 엔드포인트 호출
        restTemplate.postForEntity("/api/force-logout", entity, Void.class);
        
        // 해당 사용자의 세션이 모두 만료되었는지 확인
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(new PrincipalDetails(userInfoDTO), false);
        assertEquals(0, sessions.size());
    }
    
    @Test
    void testGetToken() throws Exception {
        // Mock 사용자 데이터
        String username = "testUser";
        String password = "testPassword";
        String encodedPassword = passwordEncoder.encode(password);

        // Mock 사용자 조회 결과
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(username);
        userInfoDTO.setPassword(encodedPassword);

        // Mock 사용자 매퍼 동작 설정
        when(userMapper.getUser(Mockito.any())).thenReturn(userInfoDTO);

        // 테스트 요청 본문 데이터
        UserInfoDTO userInfoDTOReq = new UserInfoDTO();
        userInfoDTOReq.setUsername(username);
        userInfoDTOReq.setPassword(password);

        // POST /getToken 요청 수행
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/getToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userInfoDTOReq)))
//        		.content("{\"username\":\"user1\", \"password\":\"password1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // 응답에서 토큰 추출
        String token = result.getResponse().getContentAsString();
        
        // 토큰이 비어있지 않은지 확인
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
    
    @Test
    void testGetToken_rest() throws Exception {
        // Mock 사용자 데이터
        String username = "testUser";
        String password = "testPassword";
        String encodedPassword = passwordEncoder.encode(password);

        // Mock 사용자 조회 결과
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .username(username)
                .password(encodedPassword)
                .build();

        // Mock 사용자 매퍼 동작 설정
        when(userMapper.getUser(Mockito.any())).thenReturn(userInfoDTO);

        // 테스트 요청 본문 데이터
        UserInfoDTO userInfoDTOReq = UserInfoDTO.builder()
                .username(username)
                .password(password)
                .build();

        // POST /getToken 요청 수행
        ResponseEntity<String> response = restTemplate.postForEntity("/api/getToken", userInfoDTOReq, String.class);

        // 응답에서 토큰 추출
        String token = response.getBody();
        
        // 응답 상태코드 확인
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // 토큰이 비어있지 않은지 확인
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
    
    private String getToken(String username, String password) {
        // Mock 사용자 조회 결과
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        // Mock 사용자 매퍼 동작 설정
        when(userMapper.getUser(Mockito.any())).thenReturn(userInfoDTO);

        // 테스트 요청 본문 데이터
        UserInfoDTO userInfoDTOReq = UserInfoDTO.builder()
                .username(username)
                .password(password)
                .build();

        // POST /getToken 요청 수행
        ResponseEntity<String> response = restTemplate.postForEntity("/api/getToken", userInfoDTOReq, String.class);

        // 응답에서 토큰 추출
        return response.getBody();
    }
    
    private void createUserSession(String username) {
        UserDetails userDetails = new PrincipalDetails(UserInfoDTO.builder()
                .username(username)
                .password("password")
                .role("USER")
                .language("ko")
                .build());

        String sessionId = UUID.randomUUID().toString();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
        // PrincipalDetails에 세션 아이디 설정
        PrincipalDetails principalDetails = (PrincipalDetails) userDetails;
        principalDetails.setSessionId(sessionId);

        // 세션 정보를 포함한 인증 객체 설정
        authentication.setDetails(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        sessionRegistry.registerNewSession(sessionId, userDetails);
    }

    
}