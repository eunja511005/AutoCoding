package com.eun.tutorial.controller.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eun.tutorial.dto.chat.IdeaDTO;
import com.eun.tutorial.dto.chat.IdeaListRequest;
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
    private static final String INVALID_TOKEN = "Invalid token";
    
	@PostMapping("/api/idea/list")
    public @ResponseBody ApiResponse<Map<String, Object>> getChatList(@RequestHeader("Authorization") String authToken, @RequestBody IdeaListRequest ideaListRequest) {
		String token = authToken.substring(7); // "Bearer " 이후의 토큰 부분 추출
    	
		Map<String, Object> res = new HashMap<>();
    	if (JwtTokenUtil.validateToken(token)) {
    		List<IdeaDTO> ideaDTOList = ideaService.getIdeaList(ideaListRequest);
    		long totalElements = ideaService.getTotalIdeas();
    		
    		res.put("ideaDTOList", ideaDTOList);
    		res.put("totalElements", totalElements);
    		return new ApiResponse<>(true, "Success search", res);
    	}else {
    		 // 토큰이 유효하지 않은 경우 예외 처리
            throw new IllegalArgumentException(INVALID_TOKEN);
    	}
    }
	
	@PostMapping("/api/idea")
	public @ResponseBody ApiResponse<Map<String, Object>> saveIdea(
			@RequestHeader("Authorization") String token,
			MultipartHttpServletRequest multipartFiles, 
			@RequestPart("idea") IdeaDTO ideaDTO) throws IOException {
		
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
            throw new IllegalArgumentException(INVALID_TOKEN);
        }
	}
	
	@DeleteMapping("/api/idea/{ideaId}")
	public @ResponseBody ApiResponse<Map<String, Object>> deleteIdea(@RequestHeader("Authorization") String token,
			@PathVariable String ideaId) throws IOException {
		
		String authToken = token.substring(7); // "Bearer " 이후의 토큰 부분 추출
		
		if (JwtTokenUtil.validateToken(authToken)) {// 토큰이 유효한 경우 
			String username = JwtTokenUtil.extractUsername(authToken);
			int result = ideaService.deleteIdea(ideaId, username);
			if(result==0) {
				return new ApiResponse<>(true, "No authorization to delete.", null);
			}
			return new ApiResponse<>(true, "Success delete", null);
		} else {
			// 토큰이 유효하지 않은 경우 예외 처리
			throw new IllegalArgumentException(INVALID_TOKEN);
		}
	}
}

