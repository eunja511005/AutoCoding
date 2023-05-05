package com.eun.tutorial.dto.project;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectListResponse {
    private List<ProjectDTO> projects;
    private long totalElements;

    public ProjectListResponse(List<ProjectDTO> projects, long totalElements) {
        this.projects = projects;
        this.totalElements = totalElements;
    }
}
