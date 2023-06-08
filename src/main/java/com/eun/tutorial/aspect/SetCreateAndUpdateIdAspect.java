package com.eun.tutorial.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.eun.tutorial.util.AuthUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SetCreateAndUpdateIdAspect {

	@Before("@annotation(com.eun.tutorial.aspect.annotation.SetCreateAndUpdateId) && args(dto)")
	public void setCreateAndUpdateId(JoinPoint joinPoint, Object dto) throws Throwable {
		String userId = AuthUtils.getLoginUser();
		
		Method setCreateIdMethod = dto.getClass().getMethod("setCreateId", String.class);
		Method setUpdateIdMethod = dto.getClass().getMethod("setUpdateId", String.class);

		setCreateIdMethod.invoke(dto, userId);
		setUpdateIdMethod.invoke(dto, userId);

	}
}
