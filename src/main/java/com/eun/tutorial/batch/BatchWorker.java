package com.eun.tutorial.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.batch.BatchMasterDTO;
import com.eun.tutorial.service.batch.BatchMasterService;

@Component
public abstract class BatchWorker implements Runnable{
    @Autowired
    protected BatchMasterService batchMasterService;
    
	protected BatchMasterDTO batchMasterDTO;
	
	public void setBatchMasterDTO(BatchMasterDTO batchMasterDTO) {
		this.batchMasterDTO = batchMasterDTO;
	}
	
	@Override
	public void run() {

		// 배치 수행중 상태로 변경
		batchMasterService.updateBatchMasterInprogress(batchMasterDTO);

		doBatch();
		
		// 배치 실행 가능 상태로 변경
		batchMasterService.updateBatchMasterReady(batchMasterDTO);
		
	}
	
	public abstract void doBatch();

}
