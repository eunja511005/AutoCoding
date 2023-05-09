package com.eun.tutorial.controller.main;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.DataTableResult;
import com.eun.tutorial.dto.main.PostDTO;
import com.eun.tutorial.dto.main.PostSearchDTO;
import com.eun.tutorial.service.main.PostService;

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
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
		    return false;
		} else {
		    return true;
		}
	}
	
    
	@PostMapping("/save")
	public @ResponseBody Map<String, Object> getUserProfile(@RequestBody PostDTO postDTO){
		return postService.save(postDTO);
	}
	
	@DeleteMapping("/delete/{id}")
	public @ResponseBody Map<String, Object> delete(@PathVariable String id){
    	
    	Map<String, Object> res = postService.delete(id);
    	
		return res;
	}
}