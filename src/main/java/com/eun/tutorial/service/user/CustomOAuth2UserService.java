package com.eun.tutorial.service.user;


import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.CustomOAuth2User;
import com.eun.tutorial.dto.OAuthAttributes;
import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.mapper.TestMapper;
import com.eun.tutorial.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final TestMapper testDao;
	private final UserMapper userDao;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        UserInfoDTO userInfoDTO = saveOrUpdate(attributes);
        return new PrincipalDetails(userInfoDTO, oAuth2User.getAttributes());
    }

    // 카카오나 구글에서 보내준 정보로 유저 있는지 조회해서 있으면 반환하고 없으면 생성 한다.
    // 생성시 기본 유저롤을 부여 한다.
    private UserInfoDTO saveOrUpdate(OAuthAttributes attributes){
		Map<String, Object> map = new HashMap<>();
		map.put("username", attributes.getName()); // 가져온 데이터에 키와 벨류값을 지정
        UserInfoDTO userInfoDTO = testDao.getUser(map);
        
        if(userInfoDTO == null) {
        	userInfoDTO = UserInfoDTO.builder().createId("OAuth2")
        									   	.updateId("OAuth2")
									        	.isEnable(true)
									        	.username(attributes.getName())
									        	.email(attributes.getEmail())
									        	.picture(attributes.getPicture())
									        	.role("ROLE_USER")
									        	.build();
        	userDao.addUser(userInfoDTO);
        }else { // 이미지가 업데이트 되었을 수도 있으므로 merge 해주는게 맞을거 같음
        	userInfoDTO = UserInfoDTO.builder().createId("OAuth2")
				   	.updateId("OAuth2")
		        	.isEnable(true)
		        	.username(attributes.getName())
		        	.email(attributes.getEmail())
		        	.picture(attributes.getPicture())
		        	.role("ROLE_USER")
		        	.build();
        	userDao.addUser(userInfoDTO);
        }
        
        return userInfoDTO;
        
    }
}