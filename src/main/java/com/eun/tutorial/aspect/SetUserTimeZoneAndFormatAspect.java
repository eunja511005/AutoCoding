package com.eun.tutorial.aspect;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.eun.tutorial.mapper.main.AccessControlMapper;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SetUserTimeZoneAndFormatAspect {

	@Around("@annotation(com.eun.tutorial.aspect.annotation.SetUserTimeZoneAndFormat)")
	public Object setUserTimeZoneAndFormat(ProceedingJoinPoint joinPoint) throws Throwable {

		Object result = joinPoint.proceed();
		
        if (result instanceof List) {
            List<?> resultList = (List<?>) result;
            for (Object dto : resultList) {
                performDateTimeConversion(dto);
            }
        } else {
            performDateTimeConversion(result);
        }

		return result;
	}

    private void performDateTimeConversion(Object dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
    	
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().endsWith("Dt") || field.getName().endsWith("Time") || field.getName().endsWith("At")) {
                try {
                	field.setAccessible(true); // Set the field as accessible temporarily
                    String fieldValue = (String) field.get(dto);
                    if (fieldValue != null && userDetailsImpl.getUserTimeZone() != null) {
    	                // 사용자가 정의한 형식 또는 기본 형식을 가져옴
    	                String formatterPattern = userDetailsImpl.getDateTimeFormatter() != null ?
    	                		userDetailsImpl.getDateTimeFormatter() : "yyyy년 MM월 dd일 HH:mm";
    	                
    	                // 마지막 로그인 시간을 변환하기 위한 DateTimeFormatter 설정
    	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	                LocalDateTime lastLoginDateTime = LocalDateTime.parse(fieldValue, formatter);
    	                
    	                // 시스템의 타임존 설정
    	                ZoneId userZone = ZoneId.of(System.getProperty("user.timezone"));
    	                log.debug("##### SYSTEM TIME ZONE : {}", userZone);
    	                
    	                // 유저의 타임존 설정
    	                ZoneId userTimeZone = ZoneId.of(userDetailsImpl.getUserTimeZone());
    	                log.debug("##### USER TIME ZONE : {}", userTimeZone);
    	                
    	                // 최종 로그인 시간을 시스템의 타임존으로 변환한 ZonedDateTime
    	                ZonedDateTime lastLoginZonedDateTime = lastLoginDateTime.atZone(userZone);
    	                log.debug("##### AS_IS_TIME : {}", lastLoginZonedDateTime.toString());
    	                
    	                // 최종 로그인 시간을 유저의 타임존으로 변환
    	                ZonedDateTime convertedZonedDateTime = lastLoginZonedDateTime.withZoneSameInstant(userTimeZone);
    	                log.debug("##### TO_BE_TIME : {}", convertedZonedDateTime.toString());
    	                
    	                // 변환된 시간을 지정된 형식으로 포맷팅
    	                String formattedDateTime = convertedZonedDateTime.format(DateTimeFormatter.ofPattern(formatterPattern));
    	                log.debug("##### Format_TIME : {}", formattedDateTime);
    	                
                        // 변환된 값을 필드에 다시 설정
                        field.set(dto, formattedDateTime);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false); // Reset the field accessibility back to its original state
                }
            }
        }
    }
}
