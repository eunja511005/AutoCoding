package com.eun.tutorial.controller.main;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.EmailRequestDTO;
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.service.main.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmailRestController {

    private final EmailService emailService;

    @PostMapping("/sendEmail")
    public @ResponseBody ApiResponse<String> sendEmail(@RequestParam("files") List<MultipartFile> files, 
    		EmailRequestDTO emailRequest) throws CustomException {
    	
        try {
            return new ApiResponse<>(true, "Successfully search the common code data.", emailService.sendSimpleEmail(emailRequest, files));
        } catch (Exception e) {
        	throw new CustomException(ErrorCode.Fail_SEND_EMAIL, e);
        }
        
    }
}