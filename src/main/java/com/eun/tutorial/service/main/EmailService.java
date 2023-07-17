package com.eun.tutorial.service.main;

public interface EmailService {
	String sendSimpleEmail(String to, String subject, String body);
}
