package com.eun.tutorial.filter;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class XssFilter implements Filter {

	private AntiSamy antiSamy;
	private ZthhErrorService zthhErrorService;
	
	public XssFilter(ResourceLoader resourceLoader, ZthhErrorService zthhErrorService) {
		try {
			Resource resource = resourceLoader.getResource("classpath:antisamy.xml");
			InputStream inputStream = resource.getInputStream();
			Policy policy = Policy.getInstance(inputStream);
			this.antiSamy = new AntiSamy(policy);
			this.zthhErrorService = zthhErrorService;
		} catch (Exception e) {
			log.error("Error initializing AntiSamy", e);
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		try {
			XssRequestWrapper wrappedRequest = new XssRequestWrapper(request, antiSamy, zthhErrorService);
			filterChain.doFilter(wrappedRequest, response);
		} catch (PolicyException | IOException e) {

			String errorMessage = org.apache.tika.utils.ExceptionUtils.getStackTrace(e);

			if (errorMessage.length() > 2000) {
				errorMessage = errorMessage.substring(0, 2000);
			}

			zthhErrorService.save(
					ZthhErrorDTO.builder().errorMessage("GlobalExceptionHandler Error : " + errorMessage).build());

			e.printStackTrace();
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
