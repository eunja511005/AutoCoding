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
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.service.ZthhErrorService;
import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.service.main.UserRequestHistoryService;
import com.eun.tutorial.util.AuthUtils;
import com.eun.tutorial.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = ((HttpServletRequest) servletRequest);
		HttpServletResponse response = ((HttpServletResponse) servletResponse);
		
		String uri = request.getRequestURI();
        if (uri.equals("/api/getToken") || uri.equals("/api/check_token")) { // 토큰을 얻거나 유효성 검증 할때는 헤더 체크 안하도록 함
        	filterChain.doFilter(servletRequest, servletResponse);
        }
		
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Header Name: " + headerName + ", Header Value: " + headerValue);
        }
        
		String header = request.getHeader("Authorization");
		
        if (header == null || !header.startsWith("Bearer ")) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Resource Authorization");
            return;
        }

        String authToken = header.substring(7); // "Bearer " 이후의 토큰 부분 추출
        
    	if (JwtTokenUtil.validateToken(authToken)) {
            // 토큰이 유효한 경우 데이터 반환
    		String username = JwtTokenUtil.extractUsername(authToken);
    		filterChain.doFilter(servletRequest, servletResponse);
        } else {
        	// 토큰이 유효하지 않은 경우 연결 거부
            throw new IllegalStateException("Invalid JWT token");
        }
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
