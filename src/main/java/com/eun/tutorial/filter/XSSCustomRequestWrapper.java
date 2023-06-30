package com.eun.tutorial.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XSSCustomRequestWrapper extends HttpServletRequestWrapper {
	
	private ZthhErrorService zthhErrorService;

	public XSSCustomRequestWrapper(HttpServletRequest request, ZthhErrorService zthhErrorService) {
        super(request);
        this.zthhErrorService = zthhErrorService;
    }
    
    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream inputStream = super.getInputStream();
        String contentType = super.getContentType();
        log.debug("##### contentType detected: {}", contentType);
        if (contentType != null && contentType.startsWith("application/json")) {
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.debug("##### json detected: {}", json);
            String sanitizedJson = cleanXSS(json);
            ByteArrayInputStream bis = new ByteArrayInputStream(sanitizedJson.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                	// this is not needed.
                }

                @Override
                public int read() throws IOException {
                    return bis.read();
                }
            };
        } else {
            return super.getInputStream();
        }
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return new String[0];
        }

        int count = values.length;
        String[] encodedValues = new String[count];

        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }

        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);

        if (value == null) {
            return null;
        }

        return cleanXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);

        if (value == null) {
            return null;
        }

        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        boolean idDetected = false;
        
        // Escape XSS characters
        if (value.contains("<")) {
            log.warn("##### XSS detected: {}", value);
            value = value.replace("<", "&lt;");
            idDetected = true;
        }
        if (value.contains(">")) {
            log.warn("##### XSS detected: {}", value);
            value = value.replace(">", "&gt;");
            idDetected = true;
        }
        if (value.contains("&")) {
        	log.warn("##### XSS detected: {}", value);
        	value = value.replace("&", "&amp;");
        	idDetected = true;
        }

        // Escape SQL injection characters
        if (value.contains("'")) {
            log.warn("##### SQL injection detected: {}", value);
            value = value.replace("'", "''");
            idDetected = true;
        }
        if (value.contains("--")) {
        	log.warn("##### SQL injection detected: {}", value);
        	value = value.replace("--", "__");
        	idDetected = true;
        }

        // Filter file system manipulation characters(댓글에 마침표는 들어 갈수 있어서 일단 제외)
		/*
		 * if (value.contains("..")) {
		 * log.warn("##### File system manipulation detected: {}", value); value =
		 * value.replace("\\.\\.", ""); idDetected = true; }
		 */
        
        if(idDetected) {
        	zthhErrorService.save(
        			ZthhErrorDTO.builder().errorMessage("Detecting security-sensitive characters in input values : " + value).build());
        }

        return value;
    }

}
