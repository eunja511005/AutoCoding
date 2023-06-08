package com.eun.tutorial.util;

import java.util.Collection;
import java.util.Locale;

import org.hamcrest.core.IsInstanceOf;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.eun.tutorial.service.user.PrincipalDetails;

public class AuthUtils {
	private static final String ANONYMOUS = "anonymous";
	
    public static String getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || !authentication.isAuthenticated()) {
			return ANONYMOUS;
		}else {
			Object principal = authentication.getPrincipal();
			if(principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}else {
				return ANONYMOUS;
			}
		}
    }
    
    public static Collection<? extends GrantedAuthority> getAuthorities() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication == null || !authentication.isAuthenticated()) {
    		return AuthorityUtils.NO_AUTHORITIES;
    	}else {
    		return authentication.getAuthorities();
    	}
    }
    
    public static PrincipalDetails getPrincipalDetails() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication == null || !authentication.isAuthenticated()) {
    		return null;
    	}else {
    		return (authentication.getPrincipal() instanceof PrincipalDetails) 
    			? (PrincipalDetails) authentication.getPrincipal()
    			: null;
    	}
    }
    
	public static Locale getLocale() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return Locale.KOREA;
		} else {
			PrincipalDetails userDetailsImpl = getPrincipalDetails();
			if(userDetailsImpl == null) {
				return Locale.KOREA;
			}
			String language = "ko";
			if (!StringUtils.isBlank(userDetailsImpl.getLanguage())) {
				language = userDetailsImpl.getLanguage();
			}
			return new Locale(language);
		}
	}
}
