package com.eun.tutorial.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import com.eun.tutorial.filter.XssFilter;
import com.eun.tutorial.service.ZthhErrorService;

@Configuration
public class MyFilterConfiguration {
    @Autowired
    private ResourceLoader resourceLoader;
    
	@Autowired
	private ZthhErrorService zthhErrorService;
	
    @Bean
    public FilterRegistrationBean<XssFilter> myFilter() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XssFilter(resourceLoader, zthhErrorService));
        registration.addUrlPatterns("/*"); // Set the URL patterns for the filter
        registration.setName("XssFilter");
        registration.setOrder(1); // Set the order in which the filter should be applied
        return registration;
    }
}
