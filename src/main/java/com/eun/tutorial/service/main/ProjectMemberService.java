package com.eun.tutorial.service.main;

import java.util.List;

import com.eun.tutorial.dto.main.ProjectMemberDTO;

public interface ProjectMemberService {
	List<ProjectMemberDTO> getProjectMemberList();
	int saveProjectMember(ProjectMemberDTO projectMemberDTO);
	int deleteProjectMember(String id);
	ProjectMemberDTO getProjectMemberListById(String id);
}