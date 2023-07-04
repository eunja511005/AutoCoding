package com.eun.tutorial.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
    
    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, AuthUtils.getLocale());
    }
}

