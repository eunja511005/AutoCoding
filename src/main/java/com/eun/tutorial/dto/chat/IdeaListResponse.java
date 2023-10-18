package com.eun.tutorial.dto.chat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdeaListResponse {
	private boolean success;
    private List<IdeaDTO> ideaList;
    private long totalElements;

    public IdeaListResponse(boolean success, List<IdeaDTO> ideaList, long totalElements) {
        this.success = success;
    	this.ideaList = ideaList;
        this.totalElements = totalElements;
    }
}
