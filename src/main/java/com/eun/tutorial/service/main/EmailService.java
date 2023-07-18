package com.eun.tutorial.service.main;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.eun.tutorial.dto.main.EmailRequestDTO;

public interface EmailService {
	String sendSimpleEmail(String to, String subject, String body);
	String sendSimpleEmail(EmailRequestDTO emailRequest, List<MultipartFile> files) throws IOException, MessagingException;
}
