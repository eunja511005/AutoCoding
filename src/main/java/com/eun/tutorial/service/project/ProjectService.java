package com.eun.tutorial.service.project;

import com.eun.tutorial.dto.project.ProjectDTO;
import com.eun.tutorial.dto.project.ProjectListRequest;
import com.eun.tutorial.dto.project.ProjectListResponse;
import com.eun.tutorial.service.user.PrincipalDetails;

public interface ProjectService {
    ProjectListResponse getProjects(ProjectListRequest request);
	void createProject(ProjectDTO project);
	ProjectDTO getProjectById(String id);
	void delete(String id, PrincipalDetails principalDetails);
}

