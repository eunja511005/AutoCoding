package com.eun.tutorial.service.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.eun.tutorial.dto.UserInfoDTO;

public class PrincipalDetails implements UserDetails, OAuth2User{

	private Map<String, Object> attributes;
	
    private UserInfoDTO userInfoDTO;

    //일반 로그인할때 사용하는 생성자
    public PrincipalDetails(UserInfoDTO userInfoDTO){
        this.userInfoDTO = userInfoDTO;
    }
    // 소셜로그인(OAuth2.0사용)할때 사용하는 생성자
    public PrincipalDetails(UserInfoDTO userInfoDTO, Map<String, Object> attributes){
    	this.userInfoDTO = userInfoDTO;
    	this.attributes = attributes;
    }
	
	@Override
	public Map<String, Object> getAttributes() { // OAuthe2에서 사용
		return attributes;
	}

	@Override
	public String getName() {
		return userInfoDTO.getUsername(); // OAuthe2에서 사용
	}
	
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof UserDetailsImpl)) return false;
        UserDetailsImpl userDetails = (UserDetailsImpl) o;
        return userInfoDTO.getUsername().equals(userDetails.getUsername()) &&
               userInfoDTO.getPassword().equals(userDetails.getPassword());
    }

    @Override
    public int hashCode(){
        return Objects.hash(userInfoDTO.getUsername(), userInfoDTO.getPassword());
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : userInfoDTO.getRole().split(",")){
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
	}

	@Override
	public String getPassword() {
		return userInfoDTO.getPassword();
	}

	@Override
	public String getUsername() {
		return userInfoDTO.getUsername();
	}
	
    public String getPicture() {
    	return userInfoDTO.getPicture();
    }

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		return userInfoDTO.isEnable();
	}

}