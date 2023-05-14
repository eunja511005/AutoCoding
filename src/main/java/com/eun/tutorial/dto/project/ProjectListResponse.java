package com.eun.tutorial.dto.project;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectListResponse {
	private boolean success;
    private List<ProjectDTO> projects;
    private long totalElements;

    public ProjectListResponse(boolean success, List<ProjectDTO> projects, long totalElements) {
        this.success = success;
    	this.projects = projects;
        this.totalElements = totalElements;
    }
}
