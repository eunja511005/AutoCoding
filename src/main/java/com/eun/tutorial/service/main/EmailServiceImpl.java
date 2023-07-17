package com.eun.tutorial.service.main;

import org.springframework.stereotype.Service;

import com.eun.tutorial.util.EmailUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final EmailUtils emailUtils;

    public String sendSimpleEmail(String to, String subject, String body) {
    	emailUtils.sendEmail(to, subject, body);
    	return "Email sent successfully!";
    }
}
