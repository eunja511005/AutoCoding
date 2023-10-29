package com.eun.tutorial.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.batch.BatchMasterDTO;
import com.eun.tutorial.service.batch.BatchMasterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@EnableAsync
public class MyScheduler {
	
	private final BatchMasterService batchMasterService;
    private final ThreadPoolTaskExecutor taskExecutor; // 미리 구성한 스레드 풀 주입
	private final Object lock = new Object(); // 동기화에 사용될 객체

	private final BatchWorker sendMessageBatchWorker;
	
	
	@Scheduled(fixedRate = 1000)
    public void doBatch() {
        synchronized (lock) {
            // 1. 동기화 블록 시작
            // 2. 동시에 실행되면 안 되는 작업 내용 실행
        	
        	Map<String, BatchWorker> batchMap = new HashMap<>();
        	batchMap.put("SendMessageScheduler", sendMessageBatchWorker);
        	
        	List<BatchMasterDTO> batchMasterList = batchMasterService.getBatchMasterList();
        	for (BatchMasterDTO batchMasterDTO : batchMasterList) {
        		BatchWorker batchWorker = batchMap.get(batchMasterDTO.getBatchName());
        		batchWorker.setBatchMasterDTO(batchMasterDTO);
        		taskExecutor.execute(batchWorker);
    		}
        	
        	// 3. 동기화 블록 종료
        }
		
    }
}
