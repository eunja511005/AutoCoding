package com.eun.tutorial.aspect;

import java.lang.reflect.Method;
import java.util.Collection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.mapper.main.AccessControlMapper;
import com.eun.tutorial.util.AuthUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizationAspect {

    private final AccessControlMapper accessControlMapper;

    @Around("@annotation(com.eun.tutorial.aspect.annotation.CheckAuthorization) && args(dto)")
    public Object authorize(ProceedingJoinPoint joinPoint, Object dto) throws Throwable {
        
        Collection<? extends GrantedAuthority> authorities = AuthUtils.getAuthorities();
        boolean hasRoleSys = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_SYS"));

        if (hasRoleSys) {
        	return joinPoint.proceed();
        } else {
            String loginId = AuthUtils.getLoginUser();
            
            String resourceId = "";
            if (dto instanceof String) {//delete
            	resourceId = (String) dto;
            }else {//update
                Method getIdMethod = dto.getClass().getMethod("getId");
                resourceId = (String) getIdMethod.invoke(dto);
            }
            
            //실행되는 함수 이름을 가져오고 출력
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();

            // 맵핑 테이블에서 해당 글과 사용자의 권한 정보를 조회
            log.info("##### checkUDAuthorization : "+method.getName()+", "+loginId+", "+resourceId);
            int mappingTable = accessControlMapper.getAccessControlListByResource(resourceId, loginId);
            
            if (mappingTable < 1) {
                throw new CustomException(ErrorCode.NO_AUTHORIZATION, new RuntimeException("No Resource Authorization"));
            }
            
            Object result = joinPoint.proceed();
            
            if(method.getName().startsWith("delete")) {
            	log.info("##### deleteUDAutorization : "+method+", "+loginId+", "+resourceId);
            	accessControlMapper.deleteByResourceIdUserId(resourceId, loginId);
            }
            
            return result;
        }
        
        

        
        
        

        
        
    }
}
