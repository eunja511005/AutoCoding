package com.eun.tutorial.mapper.batch;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eun.tutorial.dto.batch.BatchMasterDTO;

@Mapper
public interface ZthmBatchMasterMapper {
	List<BatchMasterDTO> selectBatchMasterList();
	int updateBatchMasterInprogress(BatchMasterDTO batchMasterDTO);
	int updateBatchMasterReady(BatchMasterDTO batchMasterDTO);
}
