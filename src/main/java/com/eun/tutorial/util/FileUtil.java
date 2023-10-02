package com.eun.tutorial.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.service.ZthhFileAttachService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileUtil {

    @Value("${spring.servlet.multipart.location}")
    private String multiPathPath;
    
    @Value("${spring.servlet.multipart.max-file-size}")
    private long maxSize;
    
    private final ZthhFileAttachService zthhFileAttachService;

    private static final String[] ALLOWED_MIME_TYPES = {"image/jpeg", "image/png", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel", "application/x-tika-msoffice", "text/plain"};

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

    public String saveImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("File name cannot be null");
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
        currentDate = path+"/"+currentDate;

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
    
    public String saveImageWithOriginName(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("File name cannot be null");
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

        Path uploadPathObj = Paths.get(multiPathPath + path);
        if (!Files.exists(uploadPathObj)) {
            Files.createDirectories(uploadPathObj);
        }
        Path filePath = uploadPathObj.resolve(originalFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 이미지 URL 반환
        return "/" + path + "/" + originalFilename;
    }
    
    public Map<String, Object> saveImageWithTable(MultipartHttpServletRequest multipartFiles, String creator) throws IOException {
    	Map<String, Object> res = new HashMap<>();
    	
    	Iterator<String> fileNames = multipartFiles.getFileNames();
        String fileName = "";
        String mediaTypeString = "";
        int seq = 0;
        while (fileNames.hasNext()) {
                fileName = fileNames.next();
                log.info("requestFile {} ", fileName);
                List<MultipartFile> multipartFilesList = multipartFiles.getFiles(fileName);
                UUID uuid = UUID.randomUUID();
                String attachId = "user_attach_"+uuid;
                for (MultipartFile multipartFile : multipartFilesList) {
                	seq++;
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    String current_date = now.format(dateTimeFormatter);

                    String originalFileExtension;
                    String contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                    	res.put("result", "Could not upload the file: " + multipartFile.getOriginalFilename() + "!");
                    	return res;
                    } else {
                    	String mimeType = new Tika().detect(multipartFile.getInputStream()); //where 'file' is a File object or InputStream of the uploaded file
                    	MediaType mediaType = MediaType.parse(mimeType);
                    	mediaTypeString = mediaType.getType() + "/" + mediaType.getSubtype();

                    	if(!mediaTypeString.equals("image/jpeg") && !mediaTypeString.equals("image/png") && !mediaTypeString.equals("image/gif")) {
                    		res.put("result", "You can upload file's media type of image/jpeg, image/png");
                    		return res;
                    	}
                        log.info("tikaMimeType {} : "+mimeType);
                        originalFileExtension = MimeTypeUtils.parseMimeType(mimeType).getSubtype();
                        originalFileExtension = "."+originalFileExtension;
                        log.info("originalFileExtension {} : "+originalFileExtension);
                    }
                    String new_file_name = current_date + "/" + System.nanoTime() + originalFileExtension;
                    File newFile = new File(multiPathPath + new_file_name);
                    if (!newFile.exists()) {
                        boolean wasSuccessful = newFile.mkdirs();
                    }

                    multipartFile.transferTo(newFile);

                    log.info("Uploaded the file successfully: " + multipartFile.getOriginalFilename());
                    log.info("new file name: " + new_file_name);
                    
                    ZthhFileAttachDTO zthhFileAttachDTO = ZthhFileAttachDTO.builder()
    									                				.attachId(attachId)
    									                				.sequence(seq)
    									                				.originalFileName(multipartFile.getOriginalFilename())
    									                				.fileName(new_file_name)
    									                				.fileType(mediaTypeString)
    									                				.fileSize(multipartFile.getSize())
    									                				.filePath(multiPathPath + new_file_name)
    									                				.createId(creator)
    									                				.updateId(creator)
    									                				.build();
                    				
                    zthhFileAttachService.save(zthhFileAttachDTO);
				}
    	
        }
    	
        // 임시 파일 지우기
        File dir = new File(multiPathPath);
        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".tmp")) {
                file.delete();
            }
        }
        
        return res;
    }
    
    public Map<String, Object> saveImageWithTableWithPath(MultipartHttpServletRequest multipartFiles, String path) throws IOException {
    	Map<String, Object> res = new HashMap<>();
    	
    	Iterator<String> fileNames = multipartFiles.getFileNames();
        String fileName = "";
        String mediaTypeString = "";
        int seq = 0;
        while (fileNames.hasNext()) {
                fileName = fileNames.next();
                log.info("requestFile {} ", fileName);
                List<MultipartFile> multipartFilesList = multipartFiles.getFiles(fileName);
                UUID uuid = UUID.randomUUID();
                String attachId = "user_attach_"+uuid;
                for (MultipartFile multipartFile : multipartFilesList) {
                	seq++;
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    String current_date = now.format(dateTimeFormatter);
                    current_date = path+"/"+current_date;

                    String originalFileExtension;
                    String contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                    	res.put("result", "Could not upload the file: " + multipartFile.getOriginalFilename() + "!");
                    	return res;
                    } else {
                    	String mimeType = new Tika().detect(multipartFile.getInputStream()); //where 'file' is a File object or InputStream of the uploaded file
                    	MediaType mediaType = MediaType.parse(mimeType);
                    	mediaTypeString = mediaType.getType() + "/" + mediaType.getSubtype();

                    	if(!mediaTypeString.equals("image/jpeg") && !mediaTypeString.equals("image/png") && !mediaTypeString.equals("image/gif")) {
                    		res.put("result", "You can upload file's media type of image/jpeg, image/png");
                    		return res;
                    	}
                        log.info("tikaMimeType {} : "+mimeType);
                        originalFileExtension = MimeTypeUtils.parseMimeType(mimeType).getSubtype();
                        originalFileExtension = "."+originalFileExtension;
                        log.info("originalFileExtension {} : "+originalFileExtension);
                    }
                    String new_file_name = current_date + "/" + System.nanoTime() + originalFileExtension;
                    File newFile = new File(multiPathPath + new_file_name);
                    if (!newFile.exists()) {
                        boolean wasSuccessful = newFile.mkdirs();
                    }

                    multipartFile.transferTo(newFile);

                    log.info("Uploaded the file successfully: " + multipartFile.getOriginalFilename());
                    log.info("new file name: " + new_file_name);
                    
                    ZthhFileAttachDTO zthhFileAttachDTO = ZthhFileAttachDTO.builder()
    									                				.attachId(attachId)
    									                				.sequence(seq)
    									                				.originalFileName(multipartFile.getOriginalFilename())
    									                				.fileName(new_file_name)
    									                				.fileType(mediaTypeString)
    									                				.fileSize(multipartFile.getSize())
    									                				.filePath(multiPathPath + new_file_name)
    									                				.createId("FileUtils")
    									                				.updateId("FileUtils")
    									                				.build();
                    				
                    zthhFileAttachService.save(zthhFileAttachDTO);
                    
                    res.put("attachId", attachId);
                    res.put("attachUri", new_file_name);
				}
    	
        }
    	
        // 임시 파일 지우기
        File dir = new File(multiPathPath);
        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".tmp")) {
                file.delete();
            }
        }
        
        return res;
    }
    
}