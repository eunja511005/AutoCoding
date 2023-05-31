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

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.main.UserManageDTO;
import com.eun.tutorial.service.main.UserManageService;
import com.eun.tutorial.util.FileUtil;
import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/userManage")
@Slf4j
@AllArgsConstructor
public class UserManageController {

	private final UserManageService userManageService;
	private final FileUtil fileUtil;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/userManage");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<UserManageDTO> getUserManageList() {
		return userManageService.getUserManageList();
	}

	@PostMapping("/list/{username}")
	public @ResponseBody ApiResponse list(@PathVariable String username) {
		log.info("Post List by ID : {}", username);
		UserManageDTO userManageDTOList = userManageService.getUserManageListById(username);
		return new ApiResponse<>(true, "Successfully retrieved the userManage list.", userManageDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveUserManage(@RequestParam("file") MultipartFile file, UserManageDTO userManageDTO) throws IOException {
		userManageDTO.setPicture(fileUtil.saveImage(file));
		userManageService.mergeUser(userManageDTO);
		return new ApiResponse<>(true, "Success save", null);
	}

	@DeleteMapping("/{username}")
	public @ResponseBody ApiResponse deleteUserManage(@PathVariable String username) {
		log.info("Delete by ID : {}", username);
		userManageService.deleteUserManage(username);
		return new ApiResponse<>(true, "Successfully deleted the userManage data.", null);
	}

}

