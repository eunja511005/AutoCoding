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
import com.eun.tutorial.dto.main.SystemMasterDTO;
import com.eun.tutorial.service.main.SystemMasterService;
import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/systemMaster")
@Slf4j
@AllArgsConstructor
public class SystemMasterController {

	private final SystemMasterService systemMasterService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/systemMaster");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<SystemMasterDTO> getSystemMasterList() {
		return systemMasterService.getSystemMasterList();
	}

	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		SystemMasterDTO systemMasterDTOList = systemMasterService.getSystemMasterListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the systemMaster list.", systemMasterDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveSystemMaster(@RequestBody SystemMasterDTO systemMasterDTO) {
		if (StringUtils.isBlank(systemMasterDTO.getId())) {
			systemMasterService.saveSystemMaster(systemMasterDTO);
			return new ApiResponse<>(true, "Success save", null);
		} else {
			systemMasterService.updateSystemMaster(systemMasterDTO);
			return new ApiResponse<>(true, "Success update", null);
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteSystemMaster(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		systemMasterService.deleteSystemMaster(id);
		return new ApiResponse<>(true, "Successfully deleted the systemMaster data.", null);
	}

}
