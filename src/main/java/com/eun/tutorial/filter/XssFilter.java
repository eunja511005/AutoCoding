package com.eun.tutorial.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

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
import org.owasp.validator.html.PolicyException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XssFilter implements Filter {

	private AntiSamy antiSamy;
	private ZthhErrorService zthhErrorService;
	
	/**
	 * XSSCustomRequestWrapper로 분기될 URL 리스트
	 * sanitize 사용할 경우 써머노트 통한 이미지 업로드 불가
	 * XSSCustomRequestWrapper로 분기시 < > javascript ' .. 만 막음
	 * 
	 * 결론 : noXssUrlList : 써머노트
	 *       xssCustomUrlList : 댓글(.을 &quote로 치환해서 문제 되어서 Customer로 체크)
	 *       sanitize : 나머지 
	 */
	private List<String> noXssUrlList = Arrays.asList("/posts/save", "/url2", "/url3"); 
	private List<String> xssCustomUrlList = Arrays.asList("/posts/comment", "/project/create", "/autoCoding/generate", "/autoCoding/save"); 
	
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
		
		log.info("##### requestURI : {}", request.getRequestURI());
		log.info("##### remoteUser : {}", request.getRemoteUser());
		if(request.getUserPrincipal()!=null) {
			log.info("##### userName : {}", request.getUserPrincipal().getName());
		}

		try {
			if(noXssUrlList.contains(request.getRequestURI())){
				filterChain.doFilter(request, response);
			}else if(xssCustomUrlList.contains(request.getRequestURI())){
				XSSCustomRequestWrapper xSSCustomRequestWrapper = new XSSCustomRequestWrapper(request, zthhErrorService);
				filterChain.doFilter(xSSCustomRequestWrapper, response);
			}else {
				XSSCustomRequestWrapper xSSCustomRequestWrapper = new XSSCustomRequestWrapper(request, zthhErrorService);
				filterChain.doFilter(xSSCustomRequestWrapper, response);
//				XssRequestWrapper wrappedRequest = new XssRequestWrapper(request, antiSamy, zthhErrorService);
//				filterChain.doFilter(wrappedRequest, response);
			}
		} catch (IOException e) {

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
