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
import com.eun.tutorial.dto.main.MenuControlDTO;
import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/menuControl")
@Slf4j
@AllArgsConstructor
public class MenuControlController {

	private final MenuControlService menuControlService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/menuControl");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<MenuControlDTO> getMenuControlList() {
		return menuControlService.getMenuControlList();
	}

	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		MenuControlDTO menuControlDTOList = menuControlService.getMenuControlListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the menuControl list.", menuControlDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveMenuControl(@RequestBody MenuControlDTO menuControlDTO) {
		if (StringUtils.isBlank(menuControlDTO.getId())) {
			menuControlService.saveMenuControl(menuControlDTO);
			return new ApiResponse<>(true, "Success save", null);
		} else {
			menuControlService.updateMenuControl(menuControlDTO);
			return new ApiResponse<>(true, "Success update", null);
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteMenuControl(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		menuControlService.deleteMenuControl(id);
		return new ApiResponse<>(true, "Successfully deleted the menuControl data.", null);
	}

}
