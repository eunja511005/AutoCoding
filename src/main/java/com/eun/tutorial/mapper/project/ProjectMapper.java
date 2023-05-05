package com.eun.tutorial.mapper.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.eun.tutorial.dto.project.ProjectDTO;

@Mapper
public interface ProjectMapper {
    List<ProjectDTO> selectProjects(@Param("offset") int offset, @Param("limit") int limit);
    long selectTotalProjects();
	int insertProject(ProjectDTO project);
	int insertProjectParticipants(Map<String, Object> params);
	List<String> selectParticipantsByProjectId(String id);
	ProjectDTO selectProjectById(String id);
	int deleteProjectParticipantsByProjectId(String id);
	int delete(String id);
}

