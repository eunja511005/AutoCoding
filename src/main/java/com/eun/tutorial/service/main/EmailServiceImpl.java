package com.eun.tutorial.service.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eun.tutorial.dto.main.EmailRequestDTO;
import com.eun.tutorial.util.EmailUtils;
import com.eun.tutorial.util.FileUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final EmailUtils emailUtils;
    private final FileUtil fileUtil;

    @Override
    public String sendSimpleEmail(String to, String subject, String body) {
    	emailUtils.sendEmail(to, subject, body);
    	return "Email sent successfully!";
    }

	@Override
	public String sendSimpleEmail(EmailRequestDTO emailRequest, List<MultipartFile> files) throws IOException, MessagingException {
    	
	    List<String> fileNames = new ArrayList<>();
	    List<String> filePaths = new ArrayList<>();
	    
	    for (MultipartFile file : files) {
	        fileNames.add(file.getOriginalFilename());
	        filePaths.add(fileUtil.saveImage(file, "openImg/email"));
	    }
	    
		emailUtils.sendEmailWithAttachments(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody(), filePaths, fileNames);
    	return "Email sent successfully!";
	}
}
