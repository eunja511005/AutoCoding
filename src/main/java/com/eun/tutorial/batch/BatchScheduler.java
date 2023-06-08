package com.eun.tutorial.batch;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eun.tutorial.service.ZthhErrorService;
import com.eun.tutorial.service.main.ErrorHistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableAsync
public class BatchScheduler {
	
//	private final ZthhErrorService zthhErrorService;
	private final ErrorHistService errorHistService;

    /**
     * Cron 표현식을 사용한 작업 예약
     * 초, 분, 시, 일, 월, 주, (년)
     * 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-7)
     */
    //@Scheduled(cron = "0/60 * * * * ?")
	@Scheduled(cron = "0 0 0 * * ?")
    public void deleteBatchForZthhError() {
    	printStart("[Batch] Delete ZTHH_ERROR");
    }

	private void printStart(String jobName) {
		Date now = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String formatedNow = sdf1.format(now);
        log.info(jobName+" started - {}", formatedNow);
        
        int re = errorHistService.delete(7);
        
        log.info("Deleted Count - {}", re);
	}
}
