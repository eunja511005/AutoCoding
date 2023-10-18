package com.eun.tutorial.dto.chat;

import java.util.List;

import lombok.Data;

@Data
public class IdeaDTO {
	private String id;
    private String title;
    private String explanation;
    private String startDate;
    private String endDate;
    private String status;
    private String manager;
    private String participants;
    private String attachId;
    private String delYn;
	private String createId;
	private String createDt;
	private String updateId;
	private String updateDt;
	private List<String> imageUrlList;

}
