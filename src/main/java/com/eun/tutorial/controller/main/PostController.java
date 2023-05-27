package com.eun.tutorial.controller.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.CommentDTO;
import com.eun.tutorial.dto.main.DataTableResult;
import com.eun.tutorial.dto.main.PostDTO;
import com.eun.tutorial.dto.main.PostSearchDTO;
import com.eun.tutorial.service.main.PostService;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/posts")
@Slf4j
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/list")
    public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/list");
		return modelAndView;
    }
    
    @PostMapping("/list")
    public @ResponseBody DataTableResult<PostDTO> list(@RequestBody PostSearchDTO postSearchDTO) {
        DataTableResult<PostDTO> result = new DataTableResult<>();
        
        int start = postSearchDTO.getStart();
        int length = postSearchDTO.getLength();
        int end = start + length;
        
        List<PostDTO> postDTOs = postService.searchPosts(postSearchDTO);
        int totalCount = postService.getTotalCount(postSearchDTO);
        
        int pageNum = (start / length) + 1;
        int totalPages = (int) Math.ceil((double)totalCount/length);
        result.setDraw(postSearchDTO.getDraw());
        result.setPageLength(length);
        result.setRecordsFiltered(totalCount);
        result.setStart(start);
        result.setEnd(end);
        result.setRecordsTotal(totalCount);
        result.setPage(pageNum);
        result.setPageTotal(totalPages);
        result.setData(postDTOs);
        return result;
    }
    
	@PostMapping("/list/{id}")
	public @ResponseBody Map<String, Object> list(@PathVariable String id){
		Map<String, Object> res = postService.findById(id);
		res.put("login", checkLogin());
    	
		return res;
	}

	private boolean checkLogin() {
		// 로그인 여부 체크
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication == null || authentication instanceof AnonymousAuthenticationToken);
	}
	
    
	@PostMapping("/save")
	public @ResponseBody Map<String, Object> getUserProfile(@RequestBody PostDTO postDTO){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			postDTO.setCreateId("anonymous");
			postDTO.setVisibility("8");
		}else {
			PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
			postDTO.setCreateId(userDetailsImpl.getName());
		}
		
		return postService.save(postDTO);
	}
	
    @PostMapping("/uploadImage")
    public @ResponseBody Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return postService.saveImage(file);
    }
	
	@PostMapping("/comment")
	public @ResponseBody Map<String, Object> addComment(Authentication authentication, @RequestBody CommentDTO commentDTO){
		Map<String, Object> res = new HashMap<>();
		if(authentication==null) {
			res.put("result", "Unauthorized. Please log in to leave a comment.");
			return res;
		}
		
		if(StringUtils.isBlank(commentDTO.getContent())) {
			res.put("result", "Input contains potentially harmful content.");
			return res;
		}
		
		PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
		commentDTO.setCreateId(userDetailsImpl.getUsername());
		return postService.addComment(commentDTO);
	}
	
	@PostMapping("/comment/{id}")
	public @ResponseBody Map<String, Object> getComments(Authentication authentication, @PathVariable String id){
		return postService.findCommentsById(id, authentication);
	}
	
	@DeleteMapping("/comment/{id}")
	public @ResponseBody Map<String, Object> deleteComment(Authentication authentication, @PathVariable String id){
		return postService.deleteComment(id, authentication);
	}
	
	@DeleteMapping("/delete/{id}")
	public @ResponseBody Map<String, Object> delete(@PathVariable String id){
    	
    	Map<String, Object> res = postService.delete(id);
    	
		return res;
	}
}