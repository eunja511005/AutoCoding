package com.eun.tutorial.aspect;

import java.io.IOException;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionLoggingAspect {

	private final ZthhErrorService zthhErrorService;

    @AfterThrowing(pointcut = "execution(* com.eun..*.*(..))", throwing = "exception")
    public void handleException(JoinPoint joinPoint, Exception exception) {
    	saveErrorLog(exception);
    }
    
	private void saveErrorLog(Exception exception) {
		String errorMessage = org.apache.tika.utils.ExceptionUtils.getStackTrace(exception);

		if (errorMessage.length() > 2000) {
			errorMessage = errorMessage.substring(0, 2000);
		}

		zthhErrorService.save(
				ZthhErrorDTO.builder().errorMessage("GlobalExceptionHandler Error : " + errorMessage).build());

		exception.printStackTrace();
	}
}

