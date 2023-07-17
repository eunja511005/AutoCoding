package com.eun.tutorial.util;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.Book;
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.mapper.TestMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailUtils {
    private final TestMapper testDao;
    private JavaMailSender javaMailSender;
    private String emailUsername;
    private String emailPassword;
    
    @PostConstruct
    public void init() throws CustomException {
        List<Book> bookList = testDao.getBookLists();
        // DB에서 이메일 계정 정보 가져오기
        Optional<Book> firstBook = bookList.stream().findFirst();
        if (firstBook.isPresent()) {
            emailUsername = firstBook.get().getName();
            emailPassword = firstBook.get().getIsbn();
        } else {
            throw new CustomException(ErrorCode.INTER_SERVER_ERROR, new RuntimeException("No Email Config Information"));
        }
        
        // 이메일 발송 정보 설정
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", "true");

        // JavaMailSender 빈 설정 정보 업데이트
        javaMailSender = new JavaMailSenderImpl();
        ((JavaMailSenderImpl) javaMailSender).setJavaMailProperties(properties);
        ((JavaMailSenderImpl) javaMailSender).setUsername(emailUsername);
        ((JavaMailSenderImpl) javaMailSender).setPassword(emailPassword);
    }
    
    // 이메일 발송을 위한 메서드
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}