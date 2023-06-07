package com.eun.tutorial.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SetCreateAndUpdateIdAspect {

	@Before("@annotation(com.eun.tutorial.aspect.annotation.SetCreateAndUpdateId) && args(dto)")
	public void setCreateAndUpdateId(JoinPoint joinPoint, Object dto) throws Throwable {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = "anonymous";
		if(authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
			PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
			userId = userDetailsImpl.getName();
		}
		
		Method setCreateIdMethod = dto.getClass().getMethod("setCreateId", String.class);
		Method setUpdateIdMethod = dto.getClass().getMethod("setUpdateId", String.class);

		setCreateIdMethod.invoke(dto, userId);
		setUpdateIdMethod.invoke(dto, userId);

	}
}
