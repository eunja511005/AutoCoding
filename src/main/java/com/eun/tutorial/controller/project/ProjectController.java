package com.eun.tutorial.controller.project;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eun.tutorial.dto.project.ProjectDTO;
import com.eun.tutorial.dto.project.ProjectListRequest;
import com.eun.tutorial.dto.project.ProjectListResponse;
import com.eun.tutorial.service.project.ProjectService;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/project")
public class ProjectController {
	
	private final ProjectService projectService;
	
	@GetMapping("/listForm")
	public ModelAndView listForm(){
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/project/projectListForm");
		
		return modelAndView;
	}
	
    @PostMapping("/list")
    @ResponseBody
    public ProjectListResponse getProjectList(@RequestBody ProjectListRequest projectListRequest) {
        return projectService.getProjects(projectListRequest);
    }
    
//	@PostMapping("/list/{id}")
//	public @ResponseBody Map<String, Object> list(Authentication authentication, @PathVariable String id){
//    	log.debug("request url : /project/list/"+id);
//    	
//    	UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
//    	
//		return projectService.getProjectById(id);
//	}
    
	@GetMapping("/inputForm")
	public ModelAndView init() {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsp/project/projectInputForm");
		
		return modelAndView;
	}
	
	@GetMapping("/inputForm/{id}")
	public ModelAndView inputForm(@PathVariable String id) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("project", projectService.getProjectById(id));
		modelAndView.setViewName("jsp/project/projectInputForm");
		
		return modelAndView;
	}
	
    @PostMapping("/create")
    public @ResponseBody Map<String, Object> createProject(@RequestBody ProjectDTO project) {
    	Map<String, Object> res = projectService.createProject(project);
    	
		return res;
    }
    
	@DeleteMapping("/delete/{id}")
	public @ResponseBody Map<String, Object> delete(Authentication authentication, @PathVariable String id){
    	
		PrincipalDetails userDetailsImpl = (PrincipalDetails) authentication.getPrincipal();
    	
    	Map<String, Object> res = projectService.delete(id, userDetailsImpl);
    	
		return res;
	}
	
}
