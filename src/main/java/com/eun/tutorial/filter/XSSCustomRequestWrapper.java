package com.eun.tutorial.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSCustomRequestWrapper extends HttpServletRequestWrapper {

    public XSSCustomRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
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
        // Remove potentially malicious characters
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        // Remove potentially malicious scripts
        value = value.replaceAll("(?i)javascript:", "");

        return value;
    }
}
