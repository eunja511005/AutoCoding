/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the developer.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The developer shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

package com.eun.tutorial.controller.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.ErrorHistDTO;
import com.eun.tutorial.service.main.ErrorHistService;
import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/errorHist")
@Slf4j
@AllArgsConstructor
public class ErrorHistController {

	private final ErrorHistService errorHistService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/errorHist");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<ErrorHistDTO> getErrorHistList() {
		return errorHistService.getErrorHistList();
	}

	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		ErrorHistDTO errorHistDTOList = errorHistService.getErrorHistListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the errorHist list.", errorHistDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveErrorHist(@RequestBody ErrorHistDTO errorHistDTO) {
		if (StringUtils.isBlank(errorHistDTO.getId())) {
			errorHistService.saveErrorHist(errorHistDTO);
			return new ApiResponse<>(true, "Success save", null);
		} else {
			errorHistService.updateErrorHist(errorHistDTO);
			return new ApiResponse<>(true, "Success update", null);
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteErrorHist(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		errorHistService.deleteErrorHist(id);
		return new ApiResponse<>(true, "Successfully deleted the errorHist data.", null);
	}

}
