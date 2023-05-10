package com.eun.tutorial.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;
    private UserInfoDTO userInfoDTO;

    public CustomOAuth2User(OAuth2User oauth2User, UserInfoDTO userInfoDTO) {
        this.oauth2User = oauth2User;
        this.userInfoDTO = userInfoDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
//        return oauth2User.getAttributes();
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return oauth2User.getAuthorities();
        return null;
    }

    @Override
    public String getName() {
        return userInfoDTO.getUsername();
    }

    public String getEmail() {
        return userInfoDTO.getEmail();
    }

    public String getPicture() {
        return userInfoDTO.getPicture();
    }

    @Override
    public String toString() {
        return "CustomOAuth2User{" +
//                "oauth2User=" + oauth2User +
                ", userInfoDTO=" + userInfoDTO +
                '}';
    }
}
