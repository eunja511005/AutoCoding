package com.eun.tutorial.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XssRequestWrapper extends HttpServletRequestWrapper {

    private final AntiSamy antiSamy;
    private ZthhErrorService zthhErrorService;

    public XssRequestWrapper(HttpServletRequest request, AntiSamy antiSamy, ZthhErrorService zthhErrorService) throws PolicyException, IOException {
        super(request);
        this.antiSamy = antiSamy;
        this.zthhErrorService = zthhErrorService;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        return sanitize(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        return Arrays.stream(values).map(this::sanitize).toArray(String[]::new);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramMap = super.getParameterMap();
        Map<String, String[]> newParamMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String[] values = entry.getValue();
            if (values == null) {
                newParamMap.put(entry.getKey(), null);
                continue;
            }
            String[] sanitizedValues = Arrays.stream(values).map(this::sanitize).toArray(String[]::new);
            newParamMap.put(entry.getKey(), sanitizedValues);
        }
        return newParamMap;
    }

    private String sanitize(String value) {
        try {
            CleanResults cleanResults = antiSamy.scan(value);
            if (cleanResults.getNumberOfErrors() > 0) {
                // 취약한 부분을 치환하여 출력
                String safeHtml = cleanResults.getCleanHTML();
                StringBuilder sb = new StringBuilder();
                sb.append("The following XSS was founded: " + value+"\n");
                for(String errorMessage : cleanResults.getErrorMessages()) {
                	sb.append("The following ErrorMessage: " + errorMessage+"\n");
                }
                sb.append("The following XSS was changed: " + safeHtml+"\n");
                
                zthhErrorService.save(
    					ZthhErrorDTO.builder().errorMessage("XSS Error : " + sb.toString()).build());

                return safeHtml;
            }
            return cleanResults.getCleanHTML();
        } catch (ScanException | PolicyException e) {
            throw new RuntimeException("Failed to sanitize input", e);
        }
    }
}
