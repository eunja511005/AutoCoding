package com.eun.tutorial.service.main;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eun.tutorial.dto.main.ProjectMemberDTO;
import com.eun.tutorial.mapper.main.ProjectMemberMapper;

import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

	private final ProjectMemberMapper projectMemberMapper;

	@Override
	public List<ProjectMemberDTO> getProjectMemberList() {
		return projectMemberMapper.selectProjectMemberList();
	}

	@Override
	public int saveProjectMember(ProjectMemberDTO projectMemberDTO) {
		if (StringUtils.isBlank(projectMemberDTO.getId())) {
			projectMemberDTO.setId("projectMember_"+UUID.randomUUID());
			return projectMemberMapper.insertProjectMember(projectMemberDTO);
		} else {
			return projectMemberMapper.updateProjectMember(projectMemberDTO);
		}
	}

	@Override
	public int deleteProjectMember(String id) {
		return projectMemberMapper.deleteProjectMember(id);
	}

	@Override
	public ProjectMemberDTO getProjectMemberListById(String id) {
		return projectMemberMapper.getProjectMemberListById(id);
	}

}