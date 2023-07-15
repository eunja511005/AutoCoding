package com.eun.tutorial.exception;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.service.ZthhErrorService;
import com.eun.tutorial.util.ExceptionUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomForbiddenEntryPoint implements AuthenticationEntryPoint {
	
	private final ExceptionUtils exceptionUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
    	String errorMessage = org.apache.tika.utils.ExceptionUtils.getStackTrace(e);
    	errorMessage = request.getRequestURI()+"\n"+errorMessage;

    	e.printStackTrace();
    	exceptionUtils.saveErrorLog(errorMessage);
        
        // [중요] data를 null로 해줘야 dataTable에서 다른 오류 발생 안함
        ApiResponse<ErrorCode> apiResponse = new ApiResponse<>(false, ErrorCode.ACCESS_DENIED.getMessage(), null);
    	
        JSONObject json =  new JSONObject(apiResponse);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(json);
        
    }
}
