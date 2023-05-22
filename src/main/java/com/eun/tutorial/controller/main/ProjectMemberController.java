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
import com.eun.tutorial.dto.main.ProjectMemberDTO;
import com.eun.tutorial.service.main.ProjectMemberService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/projectMember")
@Slf4j
@AllArgsConstructor
public class ProjectMemberController {

	private final ProjectMemberService projectMemberService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/projectMember");
		return modelAndView;
	}

	@PostMapping("/list")
	public @ResponseBody List<ProjectMemberDTO> getProjectMemberList() {
		return projectMemberService.getProjectMemberList();
	}

	@PostMapping("/list/{id}")
	public @ResponseBody ApiResponse list(@PathVariable String id) {
		log.info("Post List by ID : {}", id);
		ProjectMemberDTO projectMemberDTOList = projectMemberService.getProjectMemberListById(id);
		return new ApiResponse<>(true, "Successfully retrieved the projectMember list.", projectMemberDTOList);
	}

	@PostMapping("/save")
	public @ResponseBody ApiResponse saveProjectMember(@RequestBody ProjectMemberDTO projectMemberDTO) {
		projectMemberService.saveProjectMember(projectMemberDTO);
		return new ApiResponse<>(true, "Success message", null);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ApiResponse deleteProjectMember(@PathVariable String id) {
		log.info("Delete by ID : {}", id);
		projectMemberService.deleteProjectMember(id);
		return new ApiResponse<>(true, "Successfully deleted the projectMember data.", null);
	}

}
