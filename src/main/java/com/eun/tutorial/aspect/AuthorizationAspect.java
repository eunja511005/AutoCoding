package com.eun.tutorial.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.mapper.main.AccessControlMapper;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizationAspect {

    private final AccessControlMapper accessControlMapper;

    @Before("@annotation(checkAuthorization)")
    public void authorize(JoinPoint joinPoint, CheckAuthorization checkAuthorization) {
        // 맵핑 테이블에서 권한 체크를 수행하여 로그인한 사용자의 ID와 비교하는 로직을 구현합니다.
        // 필요한 정보는 joinPoint를 통해 얻을 수 있습니다.

        // 권한 체크에 필요한 정보 추출
        String resourceId = extractResourceId(joinPoint);
        String loginId = extractUserId();
        
        resourceId = resourceId.split("_")[0];
        log.info("##### 리소스 : "+resourceId);
        
        //실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("##### 메서드 : "+method.getName());

        // 맵핑 테이블에서 해당 글과 사용자의 권한 정보를 조회
        int mappingTable = accessControlMapper.getAccessControlListByResource(resourceId, loginId);
        
        if (mappingTable < 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to access this resource.");

        }
    }

    // JoinPoint에서 필요한 정보 추출하는 메소드 구현
    private String extractResourceId(JoinPoint joinPoint) {
        // JoinPoint로부터 메서드의 인자를 추출합니다.
        Object[] args = joinPoint.getArgs();
        
        // 예를 들어, 인자 중에서 첫 번째 인자가 게시물 ID인 경우
        if (args.length > 0) {
            Object firstArg = args[0];
            
            // 해당 인자의 타입과 필드를 확인하여 게시물 ID를 추출합니다.
            if (firstArg instanceof String) {
                return (String) firstArg;
            }
        }
        
        return ""; // 게시물 ID를 추출할 수 없는 경우
    }

    private String extractUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
        	PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // 사용자의 ID를 추출
        }
        return "anonymous";
    }

}
