package com.eun.tutorial.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
	
	@RequestMapping
	public String helloWorld() {
		return "Hello World!!";
	}

}
