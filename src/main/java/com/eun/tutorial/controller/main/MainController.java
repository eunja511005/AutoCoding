package com.eun.tutorial.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
	
	@GetMapping("/main")
	public ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/common/main");
		return modelAndView;
	}	
	
	@GetMapping("/content1")
	public ModelAndView content1() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/content1");
		return modelAndView;
	}	
	
	@GetMapping("/content2")
	public ModelAndView content2() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/content2");
		return modelAndView;
	}	
	
	@GetMapping("/content3")
	public ModelAndView content3() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/content3");
		return modelAndView;
	}	

}
