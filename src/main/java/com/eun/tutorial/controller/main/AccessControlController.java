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
import com.eun.tutorial.dto.main.AccessControlDTO;
import com.eun.tutorial.service.main.AccessControlService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/accessControl")
@Slf4j
@AllArgsConstructor
public class AccessControlController {

	private final AccessControlService accessControlService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/accessControl");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<AccessControlDTO> getAccessControlList() {
		return accessControlService.getAccessControlList();
	}

	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		AccessControlDTO accessControlDTOList = accessControlService.getAccessControlListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the accessControl list.", accessControlDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveAccessControl(@RequestBody AccessControlDTO accessControlDTO) {
		accessControlService.saveAccessControl(accessControlDTO);
		return new ApiResponse<>(true, "Success message", null);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteAccessControl(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		accessControlService.deleteAccessControl(id);
		return new ApiResponse<>(true, "Successfully deleted the accessControl data.", null);
	}

}
