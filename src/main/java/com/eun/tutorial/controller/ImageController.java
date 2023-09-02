package com.eun.tutorial.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eun.tutorial.dto.ImageRequestDTO;
import com.eun.tutorial.dto.ImageResponseDTO;
import com.eun.tutorial.dto.ZthhFileAttachDTO;
import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.project.ProjectListRequest;
import com.eun.tutorial.service.ZthhFileAttachService;
import com.eun.tutorial.util.FileUtil;
import com.eun.tutorial.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ImageController {
    @Value("${spring.servlet.multipart.location}")
    private String multiPathPath;
    private final FileUtil fileUtil;
    private final ZthhFileAttachService zthhFileAttachService;
    
	@PostMapping("/image/upload")
	public @ResponseBody ApiResponse<Map<String, Object>> saveUserManage(@RequestHeader("Authorization") String token,
			MultipartHttpServletRequest multipartFiles) throws IOException {
		
		String authToken = token.substring(7); // "Bearer " 이후의 토큰 부분 추출
		log.info(authToken);
		
		Map<String, Object> res;
    	if (JwtTokenUtil.validateToken(authToken)) {// 토큰이 유효한 경우 
    		String username = JwtTokenUtil.extractUsername(authToken);
    		res = fileUtil.saveImageWithTable(multipartFiles, username);
    		return new ApiResponse<>(true, "Success save", res);
        } else {
            // 토큰이 유효하지 않은 경우 예외 처리
            throw new IllegalArgumentException("Invalid token");
        }
	}
	
    @PostMapping("/image/lists")
    public @ResponseBody ApiResponse<List<ImageResponseDTO>> getProjectList(@RequestHeader("Authorization") String token,
    		@RequestBody ImageRequestDTO imageRequestDTO) throws IOException {

    	String authToken = token.substring(7); // "Bearer " 이후의 토큰 부분 추출
    	if (JwtTokenUtil.validateToken(authToken)) {// 토큰이 유효한 경우 
    		String username = JwtTokenUtil.extractUsername(authToken);
    		List<ImageResponseDTO> imageResponseDTOList = zthhFileAttachService.getFiles(imageRequestDTO, username);
    		return new ApiResponse<>(true, "Success save", imageResponseDTOList);
    	} else {
            // 토큰이 유효하지 않은 경우 예외 처리
            throw new IllegalArgumentException("Invalid token");
        }
        
    }
    
}