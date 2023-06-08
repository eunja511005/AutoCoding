package com.eun.tutorial.aspect;

import java.lang.reflect.Method;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.main.AccessControlDTO;
import com.eun.tutorial.mapper.main.AccessControlMapper;
import com.eun.tutorial.util.AuthUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CreateACLAspect {

	private final AccessControlMapper accessControlMapper;

	@Around("@annotation(com.eun.tutorial.aspect.annotation.CreatePermission) && args(dto)")
	public Object addUDPermission(ProceedingJoinPoint joinPoint, Object dto) throws Throwable {
		String loginId = AuthUtils.getLoginUser();

		Object result = joinPoint.proceed();

		Method getIdMethod = dto.getClass().getMethod("getId");
		String resourceId = (String) getIdMethod.invoke(dto);

		log.info("##### CreateACL : "+ loginId + ", " + resourceId);
		saveToACLTable(resourceId, loginId); // ACL 테이블에 리소스 ID와 유저 ID 저장

		return result;
	}

	private void saveToACLTable(String resourceId, String userId) {
		AccessControlDTO accessControlDTO = new AccessControlDTO();
		accessControlDTO.setId("accessControl_" + UUID.randomUUID());
		accessControlDTO.setUserId(userId);
		accessControlDTO.setRoleId("ROLE_SYS");
		accessControlDTO.setRelation(1);
		accessControlDTO.setResourceId(resourceId);
		accessControlDTO.setPermission("UD");
		accessControlDTO.setCreateId("CreateACLAspect.java");
		accessControlDTO.setUpdateId("CreateACLAspect.java");
		accessControlMapper.insertAccessControl(accessControlDTO);
	}
}
