package com.eun.tutorial.dto.batch;

import lombok.Data;

@Data
public class BatchMasterDTO {
	private String id;
    private String batchName;
    private String description;
    private String batchCycle;
    private String startDate;
    private String endDate;
    private String status;
    private String manager;
    private String logYn;
    private String delYn;
	private String createId;
	private String createDt;
	private String updateId;
	private String updateDt;
}
