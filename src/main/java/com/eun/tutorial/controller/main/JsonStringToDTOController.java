/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the @autoCoding.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The @autoCoding shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

package com.eun.tutorial.controller.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.AutoCodingDTO;
import com.eun.tutorial.util.JsonStringToDTOSourceUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/jsonStringToDTO")
@AllArgsConstructor
public class JsonStringToDTOController {

	private final JsonStringToDTOSourceUtils jsonStringToDTOSourceUtils;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/jsonStringToDTO");
		return modelAndView;
	}
	
	@PostMapping("/conversion")
	public @ResponseBody ApiResponse<List<AutoCodingDTO>> searchRealEstatePrice(@RequestBody Map<String, Object> searchDTO) throws JsonProcessingException{
	    String rootClassName = "Root";
		List<AutoCodingDTO> autoCodingList = new ArrayList<>();
		AutoCodingDTO autoCodingDTO = new AutoCodingDTO();
		autoCodingDTO.setSourceName(rootClassName+".java");
		autoCodingDTO.setSourceCode(jsonStringToDTOSourceUtils.conversion((String) searchDTO.get("jsonString")));
		autoCodingList.add(autoCodingDTO);
		
		return new ApiResponse<>(true, "Successfully search the common code data.", autoCodingList);
	}

}
