package com.eun.tutorial.service.project;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tika.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.project.ProjectDTO;
import com.eun.tutorial.dto.project.ProjectListRequest;
import com.eun.tutorial.dto.project.ProjectListResponse;
import com.eun.tutorial.mapper.project.ProjectMapper;
import com.eun.tutorial.service.user.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

	private final ProjectMapper projectMapper;

	@Autowired
	public ProjectServiceImpl(ProjectMapper projectMapper) {
		this.projectMapper = projectMapper;
	}

	@Override
	public ProjectListResponse getProjects(ProjectListRequest request) {
		int offset = (request.getPage() - 1) * request.getSize();
		List<ProjectDTO> projects = projectMapper.selectProjects(offset, request.getSize());
		
		
	    for (ProjectDTO project : projects) {
	        List<String> participants = projectMapper.selectParticipantsByProjectId(project.getId());
	        project.setParticipants(participants);
	    }
		
		long totalElements = projectMapper.selectTotalProjects();
		return new ProjectListResponse(true, projects, totalElements);
	}

	@Override
	public void createProject(ProjectDTO project) {

    	if(StringUtils.isBlank(project.getId())) {
    		UUID uuid = UUID.randomUUID();
    		String id = "project_"+uuid;
    		project.setId(id);
    	}

	    // 프로젝트 생성
	    projectMapper.insertProject(project);
	    
	    // 프로젝트 참여자 삭제
	    projectMapper.deleteProjectParticipantsByProjectId(project.getId());
	    
	    // 프로젝트 참여자 추가
	    Map<String, Object> params = new HashMap<>();
	    params.put("projectId", project.getId());
	    params.put("participants", project.getParticipants());
	    
	    projectMapper.insertProjectParticipants(params);
	}

	@Override
	public ProjectDTO getProjectById(String id) {
		
		ProjectDTO project = projectMapper.selectProjectById(id);
		
		List<String> participants = projectMapper.selectParticipantsByProjectId(id);
        project.setParticipants(participants);
		
		return project;
	}

	@Override
	public void delete(String id, PrincipalDetails principalDetails) {
	    Collection<? extends GrantedAuthority> authorities = principalDetails.getAuthorities();
	    boolean isAdmin = authorities.stream()
	            .anyMatch(auth -> auth.getAuthority().equals("ROLE_SYS"));
	    
	    ProjectDTO project = projectMapper.selectProjectById(id);
	    
	    // 프로젝트 메니져이거나 Admin만 삭제 가능
		if(principalDetails.getUsername().equals(project.getManager()) || isAdmin) {
			// 프로젝트 참여자 삭제
		    projectMapper.deleteProjectParticipantsByProjectId(id);
		    
			// 프로젝트 삭제 마크
			projectMapper.delete(id);
		}else {
			throw new AccessDeniedException("You do not have sufficient privileges to access this resource");
		}
    	
        
	}

}
