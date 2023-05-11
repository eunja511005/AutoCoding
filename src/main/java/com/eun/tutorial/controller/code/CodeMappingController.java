package com.eun.tutorial.controller.code;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.ZthmCommonCodeMappingDTO;
import com.eun.tutorial.service.ZthmCommonCodeMappingService;
import com.eun.tutorial.service.ZthmCommonCodeService;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/code/mapping")
public class CodeMappingController {
	
	private final ZthmCommonCodeService zthmCommonCodeService;
	private final ZthmCommonCodeMappingService zthmCommonCodeMappingService;
	
	@PostMapping("/save")
	public @ResponseBody Map<String, Object> getUserProfile(Authentication authentication, @RequestBody ZthmCommonCodeMappingDTO zthmCommonCodeMappingDTO){
    	log.debug("request url : /code/mapping/save");
    	
    	PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
    	
    	zthmCommonCodeMappingDTO.setCreateId(userDetailsImpl.getUsername());
    	zthmCommonCodeMappingDTO.setUpdateId(userDetailsImpl.getUsername());
    	
    	zthmCommonCodeMappingService.save(zthmCommonCodeMappingDTO);
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	if(zthmCommonCodeMappingDTO.getCodeMappingId()==0) {
    		res.put("result", "A new mapping has been created.");
    	}else {
    		res.put("result", "A mapping changed.");
    	}
    	
        res.put("redirectUrl", "/code/mapping/listForm");
		return res;
	}
	
	@GetMapping("/listForm")
	public ModelAndView listForm(){
		log.debug("request url : /code/mapping/listForm");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/code/codeMappingListForm");
		
		return modelAndView;
	}
	
	@PostMapping("/list")
	public @ResponseBody Map<String, Object> list(){
    	log.debug("request url : /code/mapping/list");
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	res.put("result", "findAll success");
        res.put("codeMappingList", zthmCommonCodeMappingService.findAll());
		return res;
	}
	
	@PostMapping("/codes")
	public @ResponseBody Map<String, Object> showCodeForm() {
		Map<String, Object> res = zthmCommonCodeService.findByGroupId();
    	
		return res;
	}
	
	@DeleteMapping("/delete/{id}")
	public @ResponseBody Map<String, Object> delete(Authentication authentication, @PathVariable long id){
    	log.debug("request url : /code/mapping/delete");
    	
    	zthmCommonCodeMappingService.delete(id);
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	res.put("result", "delete success");
        res.put("redirectUrl", "/code/mapping/listForm");
		return res;
	}
}
