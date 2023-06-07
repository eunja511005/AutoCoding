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

import java.util.List;
import java.util.Map;

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
import com.eun.tutorial.dto.main.UserRequestHistoryDTO;
import com.eun.tutorial.service.main.UserRequestHistoryService;
import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/userRequestHistory")
@Slf4j
@AllArgsConstructor
public class UserRequestHistoryController {

	private final UserRequestHistoryService userRequestHistoryService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/userRequestHistory");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<UserRequestHistoryDTO> getUserRequestHistoryList() {
		return userRequestHistoryService.getUserRequestHistoryList();
	}

	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		UserRequestHistoryDTO userRequestHistoryDTOList = userRequestHistoryService.getUserRequestHistoryListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the userRequestHistory list.", userRequestHistoryDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveUserRequestHistory(@RequestBody UserRequestHistoryDTO userRequestHistoryDTO) {
		if (StringUtils.isBlank(userRequestHistoryDTO.getId())) {
			userRequestHistoryService.saveUserRequestHistory(userRequestHistoryDTO);
			return new ApiResponse<>(true, "Success save", null);
		} else {
			userRequestHistoryService.updateUserRequestHistory(userRequestHistoryDTO);
			return new ApiResponse<>(true, "Success update", null);
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteUserRequestHistory(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		userRequestHistoryService.deleteUserRequestHistory(id);
		return new ApiResponse<>(true, "Successfully deleted the userRequestHistory data.", null);
	}
	
	@GetMapping("/requestData")
	public @ResponseBody ApiResponse getRequestData() {
	    try {
	        return new ApiResponse<List<Map<String, Object>>>(true, "Successfully retrieved the common code list.", userRequestHistoryService.getUserRequestHistoryCountByDate());
	    } catch (Exception e) {
	        return new ApiResponse<>(false, "Failed to retrieve the common code list.", null);
	    }
	}
	
	@GetMapping("/requestDataAnlyze")
	public @ResponseBody ApiResponse getRequestDataAnlyze() {
		try {
			return new ApiResponse<List<Map<String, Object>>>(true, "Successfully retrieved the common code list.", userRequestHistoryService.getUserRequestHistoryCountByCount());
		} catch (Exception e) {
			return new ApiResponse<>(false, "Failed to retrieve the common code list.", null);
		}
	}
}
