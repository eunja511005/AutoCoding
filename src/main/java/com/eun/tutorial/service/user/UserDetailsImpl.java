package com.eun.tutorial.service.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eun.tutorial.dto.UserInfoDTO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private UserInfoDTO userInfoDTO;

    public UserDetailsImpl(UserInfoDTO user) {
        this.userInfoDTO = user;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userInfoDTO.isEnable();
    }
}
