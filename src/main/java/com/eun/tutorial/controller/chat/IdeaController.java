package com.eun.tutorial.controller.chat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eun.tutorial.dto.chat.IdeaDTO;
import com.eun.tutorial.dto.chat.IdeaListRequest;
import com.eun.tutorial.dto.chat.IdeaListResponse;
import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.service.chat.IdeaService;
import com.eun.tutorial.util.FileUtil;
import com.eun.tutorial.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;
    private final FileUtil fileUtil;
    
	@PostMapping("/api/idea/list")
    public @ResponseBody IdeaListResponse getChatList(@RequestHeader("Authorization") String authToken, @RequestBody IdeaListRequest ideaListRequest) {
		String token = authToken.substring(7); // "Bearer " 이후의 토큰 부분 추출
    	
    	if (JwtTokenUtil.validateToken(token)) {
    		List<IdeaDTO> messages = ideaService.getIdeaList(ideaListRequest);
    		long totalElements = ideaService.getTotalIdeas();
    		return new IdeaListResponse(true, messages, totalElements);
    	}else {
    		 // 토큰이 유효하지 않은 경우 예외 처리
            throw new IllegalArgumentException("Invalid token");
    	}
    }
	
	@PostMapping("/api/idea")
	public @ResponseBody ApiResponse<Map<String, Object>> saveIdea(@RequestHeader("Authorization") String token,
			MultipartHttpServletRequest multipartFiles, @RequestPart("idea") IdeaDTO ideaDTO) throws IOException {
		
		String authToken = token.substring(7); // "Bearer " 이후의 토큰 부분 추출
		
		Map<String, Object> res;
    	if (JwtTokenUtil.validateToken(authToken)) {// 토큰이 유효한 경우 
    		String username = JwtTokenUtil.extractUsername(authToken);
    		ideaDTO.setCreateId(username);
    		ideaDTO.setUpdateId(username);
    		
    		res = fileUtil.saveImageWithTableWithPath(multipartFiles, "openImg/idea");
    		String attachId = (String) res.get("attachId");
    		ideaDTO.setAttachId(attachId);
    		ideaService.saveIdea(ideaDTO);
    		return new ApiResponse<>(true, "Success save", res);
        } else {
            // 토큰이 유효하지 않은 경우 예외 처리
            throw new IllegalArgumentException("Invalid token");
        }
	}
}

