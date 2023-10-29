package com.eun.tutorial.service.batch;

import java.util.List;

import com.eun.tutorial.dto.batch.BatchMasterDTO;

public interface BatchMasterService {
	List<BatchMasterDTO> getBatchMasterList();
	int updateBatchMasterReady(BatchMasterDTO batchMasterDTO);
	int updateBatchMasterInprogress(BatchMasterDTO batchMasterDTO);
}