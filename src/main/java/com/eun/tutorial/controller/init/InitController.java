package com.eun.tutorial.controller.init;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class InitController {
	
	@GetMapping("/initInit")
	public ModelAndView init() {
		log.debug("request url : /init");
		
		
		

		
		
        StringBuilder stringBuilder = new StringBuilder();

        String navbarHeader = "<ul class=\"navbar-nav ms-auto\">";
        String navbarFooter = "</ul>";

//        stringBuilder.append(navbarHeader);
//        ZthmMenu current;
//        for (int i=0; i<zthmMenuMap.get(null).size(); i++){
//            current = zthmMenuMap.get(null).get(i);
//            retrieve(current, zthmMenuMap, stringBuilder);
//        }
//        stringBuilder.append(navbarFooter);
		
		
		
		
		
		
		
		
		
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/init/init");
		
		return modelAndView;
	}
	
	@PostMapping("/getUserProfile")
	public @ResponseBody Map<String, Object> getUserProfile(Authentication authentication){
    	log.debug("request url : /join");
    	
    	PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
    	
    	Map<String, Object> res = new HashMap<>();
    	res.put("userProfileImg", userDetailsImpl.getPicture());
    	res.put("userName", userDetailsImpl.getUsername());
    	
    	return res;
	}
}
