package com.eun.tutorial.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.eun.tutorial.dto.main.MenuControlDTO;
import com.eun.tutorial.dto.main.UserRequestHistoryDTO;
import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.service.main.UserRequestHistoryService;
import com.eun.tutorial.util.AuthUtils;
import com.eun.tutorial.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingFilter implements Filter {

	private MenuControlService menuControlService;
	private UserRequestHistoryService userRequestHistoryService;

    public LoggingFilter(MenuControlService menuControlService, UserRequestHistoryService userRequestHistoryService) {
		this.menuControlService = menuControlService;
		this.userRequestHistoryService = userRequestHistoryService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
    	
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        UserRequestHistoryDTO userRequestHistoryDTO = new UserRequestHistoryDTO();
    	
        String url = requestWrapper.getRequestURI();
        String method = requestWrapper.getMethod();
        
		log.info("##### requestURI : {}", url);
		log.info("##### requestMethod : {}", method);

		String logYn;
		String logDataYn;
		MenuControlDTO matchMenuControlDTO = getMenuControlDTO(url, method);
		
		if (matchMenuControlDTO==null) {
			logYn = "Y";
			logDataYn = "N";
			saveMenuControl(url, method, logYn, logDataYn);
		}else {
			logYn = matchMenuControlDTO.getLogYn();
			logDataYn = matchMenuControlDTO.getLogDataYn();
		}
		
		if (logYn.equals("Y")) {

			// 요청 정보 추출
			String ip = requestWrapper.getRemoteAddr();
			String user = AuthUtils.getLoginUser();
	        
			userRequestHistoryDTO.setUrl(url);
			userRequestHistoryDTO.setMethod(method);
			userRequestHistoryDTO.setReqIp(ip);
			userRequestHistoryDTO.setReqUser(user);

			if (logDataYn.equals("Y")) {
				// 요청 데이터 추출
				String requestData="";
//		        String requestData = extractRequestData(requestWrapper);
		        
				if (requestData.length() > 2000) {
					requestData = requestData.substring(0, 2000);
				}
				
				userRequestHistoryDTO.setReqData(requestData);
			}
		}

        chain.doFilter(requestWrapper, responseWrapper);
        
        String responseContent = extractResponseData(responseWrapper);
        
		if(logDataYn.equals("Y")) {
			userRequestHistoryDTO.setResData(responseContent);
		}
		
		if(logYn.equals("Y")) {
			userRequestHistoryService.saveUserRequestHistory(userRequestHistoryDTO);
		}
        
        // 추출한 데이터를 다시 응답으로 전송하기 위해 responseWrapper에 쓰입니다.
        responseWrapper.copyBodyToResponse();
    }

//    private String extractRequestData(ContentCachingRequestWrapper requestWrapper) throws IOException {
//        StringBuilder requestData = new StringBuilder();
//
//        // 요청 URL과 메서드 추출
//        String url = requestWrapper.getRequestURL().toString();
//        String method = requestWrapper.getMethod();
//
//        requestData.append("Request URL: ").append(url).append(System.lineSeparator());
//        requestData.append("Request Method: ").append(method).append(System.lineSeparator());
//
//        // 요청 헤더 추출
//        Enumeration<String> headerNamesEnum = requestWrapper.getHeaderNames();
//        List<String> headerNamesList = Collections.list(headerNamesEnum);
//        Map<String, String> headers = new HashMap<>();
//
//        headerNamesList.forEach(headerName -> headers.put(headerName, requestWrapper.getHeader(headerName)));
//
//        if (!headers.isEmpty()) {
//            requestData.append("Request Headers: ").append(System.lineSeparator());
//            headers.forEach((headerName, headerValue) -> requestData.append(headerName).append(": ").append(headerValue).append(System.lineSeparator()));
//        }
//
//        // 요청 파라미터 추출
//        Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
//
//        if (!parameterMap.isEmpty()) {
//            requestData.append("Request Parameters: ").append(System.lineSeparator());
//            parameterMap.forEach((paramName, paramValues) -> {
//                String paramValue = Arrays.toString(paramValues);
//                requestData.append(paramName).append(": ").append(paramValue).append(System.lineSeparator());
//            });
//        }
//
//        // 요청 바디 추출
//        byte[] requestBody = requestWrapper.getContentAsByteArray();
//
//        if (requestBody.length > 0) {
//            String requestBodyString = new String(requestBody, StandardCharsets.UTF_8);
//            requestData.append("Request Body: ").append(requestBodyString).append(System.lineSeparator());
//        }
//        
//        // 요청 inputStream
//        String stream = extractRequestBody(requestWrapper);
//        if (!StringUtils.isBlank(stream)) {
//            requestData.append("Request Stream: ").append(stream).append(System.lineSeparator());
//        }
//
//        return requestData.toString();
//    }
    
    private String extractRequestBody(ContentCachingRequestWrapper requestWrapper) throws IOException {
        InputStream inputStream = requestWrapper.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        byte[] requestBodyBytes = outputStream.toByteArray();
        return new String(requestBodyBytes, "UTF-8");
    }
    
    private String extractResponseData(ContentCachingResponseWrapper responseWrapper) {
        StringBuilder responseData = new StringBuilder();

        // 응답 상태 코드 추출
        int statusCode = responseWrapper.getStatus();
        responseData.append("Response Status Code: ").append(statusCode).append(System.lineSeparator());

        // 응답 헤더 추출
        Map<String, String> headers = new HashMap<>();
        responseWrapper.getHeaderNames().forEach(headerName -> headers.put(headerName, responseWrapper.getHeader(headerName)));

        if (!headers.isEmpty()) {
            responseData.append("Response Headers: ").append(System.lineSeparator());
            headers.forEach((headerName, headerValue) -> responseData.append(headerName).append(": ").append(headerValue).append(System.lineSeparator()));
        }

        // 응답 바디 추출
        byte[] responseBody = responseWrapper.getContentAsByteArray();

        if (responseBody.length > 0) {
            String responseBodyString = new String(responseBody, StandardCharsets.UTF_8);
            responseData.append("Response Body: ").append(responseBodyString);
        }

        return responseData.toString();
    }

	private void saveMenuControl(String url, String method, String logYn, String logDataYn) {
		MenuControlDTO menuControlDTO = new MenuControlDTO();
		menuControlDTO.setId("menuControl_" + UUID.randomUUID());
		menuControlDTO.setLogYn(logYn);
		menuControlDTO.setLogDataYn(logDataYn);
		menuControlDTO.setMethod(method);
		menuControlDTO.setRoleId("ANY");
		menuControlDTO.setUrl(url);
		menuControlDTO.setCreateId("UserRequestLoggingAspect.java");
		menuControlDTO.setUpdateId("UserRequestLoggingAspect.java");
		menuControlService.saveMenuControl(menuControlDTO);
	}

	private MenuControlDTO getMenuControlDTO(String url, String method) {
	    List<MenuControlDTO> menuControlDTOList = menuControlService.getMenuControlList();

	    for (MenuControlDTO menuControlDTO : menuControlDTOList) {
	        if (menuControlDTO.getMethod().equals(method)) {
	            if (menuControlDTO.getUrl().endsWith("*")) {
	                String prefix = menuControlDTO.getUrl().substring(0, menuControlDTO.getUrl().length() - 1);
	                if (url.startsWith(prefix)) {
	                    return menuControlDTO;
	                }
	            } else {
	                if (url.equals(menuControlDTO.getUrl())) {
	                    return menuControlDTO;
	                }
	            }
	        }
	    }

	    return null;
	}
	
}