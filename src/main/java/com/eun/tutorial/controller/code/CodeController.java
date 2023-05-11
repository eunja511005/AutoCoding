package com.eun.tutorial.controller.code;

import java.util.HashMap;
import java.util.Map;

import org.apache.tika.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.eun.tutorial.dto.ZthmCommonCodeDTO;
import com.eun.tutorial.service.ZthmCommonCodeService;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/code")
public class CodeController {
	
	private static final Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	private final ZthmCommonCodeService zthmCommonCodeService;
	
	@PostMapping("/saveForm")
	public ModelAndView init() {
		logger.debug("request url : /code/saveForm");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/code/codeSaveForm");
		
		return modelAndView;
	}
	
	@PostMapping("/save")
	public @ResponseBody Map<String, Object> getUserProfile(Authentication authentication, @RequestBody ZthmCommonCodeDTO zthmCommonCodeDTO){
    	logger.debug("request url : /join");
    	
    	PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    	
    	zthmCommonCodeDTO.setCreateId(principalDetails.getUsername());
    	zthmCommonCodeDTO.setUpdateId(principalDetails.getUsername());
    	
    	zthmCommonCodeService.save(zthmCommonCodeDTO);
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	if(StringUtils.isBlank(zthmCommonCodeDTO.getId())) {
    		res.put("result", "A new code has been created.");
    	}else {
    		res.put("result", "A code changed.");
    	}
    	
        res.put("redirectUrl", "/code/listForm");
		return res;
	}
	
	@GetMapping("/listForm")
	public ModelAndView listForm(){
		logger.debug("request url : /code/listForm");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/code/codeListForm");
		
		return modelAndView;
	}
	
	@PostMapping("/list")
	public @ResponseBody Map<String, Object> list(){
    	logger.debug("request url : /join");
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	res.put("result", "findAll success");
        res.put("codeList", zthmCommonCodeService.findAll());
		return res;
	}
	
	@DeleteMapping("/delete/{id}")
	public @ResponseBody Map<String, Object> delete(Authentication authentication, @PathVariable String id){
    	logger.debug("request url : /join");
    	
    	zthmCommonCodeService.delete(id);
    	
    	Map<String, Object> res = new HashMap<>();
    	
    	res.put("result", "delete success");
        res.put("redirectUrl", "/code/listForm");
		return res;
	}
}
