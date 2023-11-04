package com.eun.tutorial.mapper.batch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.batch.BatchMasterDTO;

@Mapper
public interface ZthmBatchMasterMapper {
	List<BatchMasterDTO> selectBatchMasterList();
	int updateBatchMasterInprogress(BatchMasterDTO batchMasterDTO);
	int updateBatchMasterReady(BatchMasterDTO batchMasterDTO);
	List<BatchMasterDTO> selectBatchMasters(Map<String, Object> params);
	int getTotalCount(Map<String, Object> params);
	int insertBatchMaster(BatchMasterDTO layoutDTO);
	int updateBatchMaster(BatchMasterDTO layoutDTO);
	int deleteBatchMaster(String id);
	BatchMasterDTO getBatchMasterById(String id);
}
