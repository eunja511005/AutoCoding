package com.eun.tutorial.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.main.ProjectMemberDTO;

@Mapper
public interface ProjectMemberMapper {
	List<ProjectMemberDTO> selectProjectMemberList();
	int insertProjectMember(ProjectMemberDTO projectMemberDTO);
	int updateProjectMember(ProjectMemberDTO projectMemberDTO);
	int deleteProjectMember(String id);
	ProjectMemberDTO getProjectMemberListById(String id);
}