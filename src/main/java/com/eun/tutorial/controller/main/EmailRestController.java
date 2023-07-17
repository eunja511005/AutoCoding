package com.eun.tutorial.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.EmailRequestDTO;
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.service.main.EmailService;
import com.eun.tutorial.service.main.EmailServiceImpl;

@RestController
public class EmailRestController {

    private final EmailService emailService;

    @Autowired
    public EmailRestController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public @ResponseBody ApiResponse<String> sendEmail(@RequestBody EmailRequestDTO emailRequest) throws CustomException {
        try {
            return new ApiResponse<>(true, "Successfully search the common code data.", emailService.sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody()));
        } catch (Exception e) {
        	throw new CustomException(ErrorCode.Fail_SEND_EMAIL, e);
        }
        
    }
}