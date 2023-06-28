package com.eun.tutorial.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.service.main.UserRequestHistoryService;

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
    	
    	chain.doFilter(servletRequest, servletResponse);
    }

}