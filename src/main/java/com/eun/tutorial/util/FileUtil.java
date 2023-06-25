package com.eun.tutorial.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    @Value("${spring.servlet.multipart.location}")
    private String multiPathPath;
    
    @Value("${spring.servlet.multipart.max-file-size}")
    private long maxSize;

    private static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel"};

    public boolean isAllowedImageType(String contentType) {
        for (String allowedType : ALLOWED_IMAGE_TYPES) {
            if (allowedType.equals(contentType)) {
                return true;
            }
        }
        return false;
    }

    private static final String[] ALLOWED_MIME_TYPES = {"image/jpeg", "image/png", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel", "application/x-tika-msoffice"};

    public boolean isAllowedMimeType(String mimeType) {
        for (String allowedType : ALLOWED_MIME_TYPES) {
            if (allowedType.equals(mimeType)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllowedFileSize(MultipartFile file) {
    	return file.getSize() <= maxSize;
    }

    public String saveImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }

        // 파일 타입 체크
        String contentType = file.getContentType();
        if (!isAllowedImageType(contentType)) {
            throw new IllegalArgumentException("File type not allowed: " + contentType);
        }

        // mimeType 체크
        String mimeType = new Tika().detect(file.getInputStream());
        if (!isAllowedMimeType(mimeType)) {
            throw new IllegalArgumentException("MIME type not allowed: " + mimeType);
        }

        // 파일 크기 체크
        if (!isAllowedFileSize(file)) {
            throw new IllegalArgumentException("File size too large: " + file.getSize());
        }

        // 날짜 별로 파일 저장 하기 위해
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentDate = now.format(dateTimeFormatter);
        currentDate = "openImg/"+currentDate;

        // 파일 저장
        String originalFileExtension = MimeTypeUtils.parseMimeType(mimeType).getSubtype();
        originalFileExtension = "."+originalFileExtension;
        String newFileName = UUID.randomUUID().toString() + originalFileExtension;
        
        Path uploadPathObj = Paths.get(multiPathPath + currentDate);
        if (!Files.exists(uploadPathObj)) {
            Files.createDirectories(uploadPathObj);
        }
        Path filePath = uploadPathObj.resolve(newFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 이미지 URL 반환
        return "/" + currentDate + "/" + newFileName;
    }
}