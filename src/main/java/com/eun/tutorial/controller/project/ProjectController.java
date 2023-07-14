package com.eun.tutorial.controller.project;

import org.springframework.security.core.Authentication;
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
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.dto.project.ProjectDTO;
import com.eun.tutorial.dto.project.ProjectListRequest;
import com.eun.tutorial.dto.project.ProjectListResponse;
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.exception.ErrorCode;
import com.eun.tutorial.service.project.ProjectService;
import com.eun.tutorial.service.user.PrincipalDetails;
import com.eun.tutorial.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/project")
public class ProjectController {
	
	private final ProjectService projectService;
	private final FileUtil fileUtil;
	
	@GetMapping("/listForm")
	public ModelAndView listForm(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/projectListForm");
		
		return modelAndView;
	}
	
    @PostMapping("/list")
    @ResponseBody
    public ProjectListResponse getProjectList(@RequestBody ProjectListRequest projectListRequest) {
        return projectService.getProjects(projectListRequest);
    }
    
	@GetMapping("/inputForm")
	public ModelAndView init() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/main/content/projectInputForm");
		
		return modelAndView;
	}
	
	@GetMapping("/inputForm/{id}")
	public ModelAndView inputForm(@PathVariable String id) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("project", projectService.getProjectById(id));
		modelAndView.setViewName("jsp/main/content/projectInputForm");
		
		return modelAndView;
	}
	
    @PostMapping("/create")
    public @ResponseBody ApiResponse<ProjectDTO> createProject(@RequestParam("file") MultipartFile file, 
    		ProjectDTO project) throws CustomException {
        try {
        	project.setPicture(fileUtil.saveImage(file));
        	projectService.createProject(project);
            return new ApiResponse<>(true, "Created successfully.", null);
        } catch (Exception e) {
        	throw new CustomException(ErrorCode.INTER_SERVER_ERROR, e);
        }
    }
    
	@DeleteMapping("/delete/{id}")
	public @ResponseBody ApiResponse<ProjectDTO> delete(Authentication authentication, @PathVariable String id){
        try {
        	PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
        	projectService.delete(id, userDetailsImpl);
            return new ApiResponse<>(true, "Deleted successfully.", null);
        } catch (Exception e) {
        	return new ApiResponse<>(false, e.getMessage(), null);
        }
	}
	
}
