package com.eun.tutorial.service.batch;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.dto.batch.BatchMasterDTO;
import com.eun.tutorial.mapper.batch.ZthmBatchMasterMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class BatchMasterServiceImpl implements BatchMasterService {
	private final ZthmBatchMasterMapper zthmBatchMasterMapper;
	

	@Override
	public List<BatchMasterDTO> getBatchMasterList() {
		return zthmBatchMasterMapper.selectBatchMasterList();
	}


	@Override
	public int updateBatchMasterReady(BatchMasterDTO batchMasterDTO) {
		return zthmBatchMasterMapper.updateBatchMasterReady(batchMasterDTO);
	}
	
	@Override
	public int updateBatchMasterInprogress(BatchMasterDTO batchMasterDTO) {
		return zthmBatchMasterMapper.updateBatchMasterInprogress(batchMasterDTO);
	}

}
