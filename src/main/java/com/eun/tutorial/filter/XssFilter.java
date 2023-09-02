package com.eun.tutorial.filter;

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
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.dto.main.MenuControlDTO;
import com.eun.tutorial.dto.main.UserRequestHistoryDTO;
import com.eun.tutorial.service.ZthhErrorService;
import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.service.main.UserRequestHistoryService;
import com.eun.tutorial.util.AuthUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XssFilter implements Filter {

	private AntiSamy antiSamy;
	private ZthhErrorService zthhErrorService;
	private MenuControlService menuControlService;
	private UserRequestHistoryService userRequestHistoryService;
	
	/**
	 * XSSCustomRequestWrapper로 분기될 URL 리스트
	 * sanitize 사용할 경우 써머노트 통한 이미지 업로드 불가
	 * XSSCustomRequestWrapper로 분기시 < > javascript ' .. 만 막음
	 * 
	 * 결론 : noXssUrlList : 써머노트
	 *       xssCustomUrlList : 댓글(.을 &quote로 치환해서 문제 되어서 Customer로 체크)
	 *       sanitize : 나머지 
	 */
	private List<String> noXssUrlList = Arrays.asList("/posts/save", "/layout/save", "/project/create", "/url2", "/url3"); 
	private List<String> xssCustomUrlList = Arrays.asList("/posts/comment", "/autoCoding/generate", "/autoCoding/save"); 
	
	public XssFilter(ResourceLoader resourceLoader, ZthhErrorService zthhErrorService, 
			MenuControlService menuControlService, UserRequestHistoryService userRequestHistoryService) {
		try {
			Resource resource = resourceLoader.getResource("classpath:antisamy.xml");
			InputStream inputStream = resource.getInputStream();
			Policy policy = Policy.getInstance(inputStream);
			this.antiSamy = new AntiSamy(policy);
			this.zthhErrorService = zthhErrorService;
			this.menuControlService = menuControlService;
			this.userRequestHistoryService = userRequestHistoryService;
		} catch (Exception e) {
			log.error("Error initializing AntiSamy", e);
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        String url = requestWrapper.getRequestURI();
        String method = requestWrapper.getMethod();
		
		UserRequestHistoryDTO userRequestHistoryDTO = new UserRequestHistoryDTO();
		StringBuilder requestData = new StringBuilder();
		boolean needXSSCheck = true;
		
        // 요청 헤더 추출
        extreactHeader(requestWrapper, requestData);
		
		try {
			if(noXssUrlList.contains(requestWrapper.getRequestURI())){
				needXSSCheck = false;
				XSSCustomRequestWrapper xSSCustomRequestWrapper = new XSSCustomRequestWrapper(requestWrapper, zthhErrorService, needXSSCheck, requestData);
				filterChain.doFilter(xSSCustomRequestWrapper, responseWrapper);
			}else if(xssCustomUrlList.contains(requestWrapper.getRequestURI())){
				XSSCustomRequestWrapper xSSCustomRequestWrapper = new XSSCustomRequestWrapper(requestWrapper, zthhErrorService, needXSSCheck, requestData);
				filterChain.doFilter(xSSCustomRequestWrapper, responseWrapper);
			}else {
				XSSCustomRequestWrapper xSSCustomRequestWrapper = new XSSCustomRequestWrapper(requestWrapper, zthhErrorService, needXSSCheck, requestData);
				filterChain.doFilter(xSSCustomRequestWrapper, responseWrapper);
//				XssRequestWrapper wrappedRequest = new XssRequestWrapper(request, antiSamy, zthhErrorService);
//				filterChain.doFilter(wrappedRequest, response);
			}
			
			saveLog(requestWrapper, responseWrapper, url, method, userRequestHistoryDTO, requestData);
			
		} catch (IOException e) {
			saveFilterErrorLog(e);
		}
		
		// 추출한 데이터를 다시 응답으로 전송하기 위해 responseWrapper에 쓰입니다.
        responseWrapper.copyBodyToResponse();
		
	}

	private void saveFilterErrorLog(IOException e) {
		String errorMessage = org.apache.tika.utils.ExceptionUtils.getStackTrace(e);

		if (errorMessage.length() > 2000) {
			errorMessage = errorMessage.substring(0, 2000);
		}

		zthhErrorService.save(
				ZthhErrorDTO.builder().errorMessage("GlobalExceptionHandler Error : " + errorMessage).build());

		e.printStackTrace();
	}

	private void extreactHeader(ContentCachingRequestWrapper requestWrapper, StringBuilder requestData) {
		Enumeration<String> headerNamesEnum = requestWrapper.getHeaderNames();
        List<String> headerNamesList = Collections.list(headerNamesEnum);
        Map<String, String> headers = new HashMap<>();

        headerNamesList.forEach(headerName -> headers.put(headerName, requestWrapper.getHeader(headerName)));

        if (!headers.isEmpty()) {
            requestData.append("Request Headers: ").append(System.lineSeparator());
            headers.forEach((headerName, headerValue) -> requestData.append(headerName).append(": ").append(headerValue).append(System.lineSeparator()));
        }
	}

	private void saveLog(HttpServletRequest request, ContentCachingResponseWrapper responseWrapper, String url,
			String method, UserRequestHistoryDTO userRequestHistoryDTO, StringBuilder requestData) {
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
		
		log.info("##### logDataYn : {}", logDataYn);
		
		if (logYn.equals("Y")) {

			// 요청 정보 추출
			String ip = request.getRemoteAddr();
			String user = AuthUtils.getLoginUser();
	        
			userRequestHistoryDTO.setUrl(url);
			userRequestHistoryDTO.setMethod(method);
			userRequestHistoryDTO.setReqIp(ip);
			userRequestHistoryDTO.setReqUser(user);

			if (logDataYn.equals("Y")) {
				// 요청 데이터
		        String requestDataString = requestData.toString();
				if (requestDataString.length() > 2000) {
					requestDataString = requestDataString.substring(0, 2000);
				}
				userRequestHistoryDTO.setReqData(requestDataString);
				
				// 응답 데이터
		        String responseContent = extractResponseData(responseWrapper);
		        if (responseContent.length() > 2000) {
					responseContent = responseContent.substring(0, 2000);
				}
				userRequestHistoryDTO.setResData(responseContent);
			}
			
			userRequestHistoryService.saveUserRequestHistory(userRequestHistoryDTO);
		}
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
	
	private void saveMenuControl(String url, String method, String logYn, String logDataYn) {
		MenuControlDTO menuControlDTO = new MenuControlDTO();
		menuControlDTO.setId("menuControl_" + UUID.randomUUID());
		menuControlDTO.setLogYn(logYn);
		menuControlDTO.setLogDataYn(logDataYn);
		menuControlDTO.setMethod(method);
		menuControlDTO.setRoleId("ANY");
		menuControlDTO.setUrl(url);
		menuControlDTO.setCreateId("XssFilter.java");
		menuControlDTO.setUpdateId("XssFilter.java");
		menuControlService.saveMenuControl(menuControlDTO);
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
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do nothing
    }

    @Override
    public void destroy() {
        // Do nothing
    }

}
