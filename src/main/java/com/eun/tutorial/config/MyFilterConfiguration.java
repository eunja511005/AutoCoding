package com.eun.tutorial.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import com.eun.tutorial.filter.LoggingFilter;
import com.eun.tutorial.filter.JwtTokenCheckFilter;
import com.eun.tutorial.filter.XssFilter;
import com.eun.tutorial.service.ZthhErrorService;
import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.service.main.UserRequestHistoryService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MyFilterConfiguration {
	private final ResourceLoader resourceLoader;
    private final ZthhErrorService zthhErrorService;
	private final MenuControlService menuControlService;
	private final UserRequestHistoryService userRequestHistoryService;

    
//    @Bean
//    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
//    	FilterRegistrationBean<LoggingFilter> registration = new FilterRegistrationBean<>();
//    	registration.setFilter(new LoggingFilter(menuControlService, userRequestHistoryService));
//    	registration.addUrlPatterns("/*"); // Set the URL patterns for the filter
//    	registration.setName("LoggingFilter");
//    	registration.setOrder(1); // Set the order in which the filter should be applied
//    	return registration;
//    }
	
    @Bean
    public FilterRegistrationBean<XssFilter> myFilter() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XssFilter(resourceLoader, zthhErrorService, menuControlService, userRequestHistoryService));
        registration.addUrlPatterns("/*"); // Set the URL patterns for the filter
        registration.setName("XssFilter");
        registration.setOrder(2); // Set the order in which the filter should be applied
        return registration;
    }
    
    @Bean
    public FilterRegistrationBean<JwtTokenCheckFilter> webSocketFilter() {
    	FilterRegistrationBean<JwtTokenCheckFilter> registration = new FilterRegistrationBean<>();
    	registration.setFilter(new JwtTokenCheckFilter());
    	registration.addUrlPatterns("/ws-service", "/openImg/chat/*", "/openImg/idea/*"); // Set the URL patterns for the filter
    	registration.setName("JwtTokenCheckFilter");
    	registration.setOrder(3); // Set the order in which the filter should be applied
    	return registration;
    }

}
