package com.eun.tutorial.service.batch;

import java.util.List;
import java.util.Map;

import com.eun.tutorial.dto.batch.BatchMasterDTO;
import com.eun.tutorial.dto.main.DataTableRequestDTO;

public interface BatchMasterService {
	List<BatchMasterDTO> getBatchMasterList();
	int updateBatchMasterReady(BatchMasterDTO batchMasterDTO);
	int updateBatchMasterInprogress(BatchMasterDTO batchMasterDTO);
	List<BatchMasterDTO> searchBatchMasters(DataTableRequestDTO searchDTO);
	int getTotalCount(DataTableRequestDTO searchDTO);
	int saveBatchMaster(BatchMasterDTO layoutDTO);
	int updateBatchMaster(BatchMasterDTO layoutDTO);
	Map<String, Integer> deleteBatchMaster(List<String> idList);
	BatchMasterDTO getBatchMasterById(String id);
	Map<String, Integer> saveBatchMaster(List<BatchMasterDTO> batchMasterDTOList);
}