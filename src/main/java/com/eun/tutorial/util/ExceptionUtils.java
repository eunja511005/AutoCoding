package com.eun.tutorial.util;

import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ExceptionUtils {
    private final ZthhErrorService zthhErrorService;

    public void saveErrorLog(Exception e) {
        String errorMessage = org.apache.tika.utils.ExceptionUtils.getStackTrace(e);
        e.printStackTrace();
        saveErrorLog(errorMessage);
    }
    
    public void saveErrorLog(String errorMessage) {
		if (errorMessage.length() > 2000) {
			errorMessage = errorMessage.substring(0, 2000);
		}
		zthhErrorService.save(
				ZthhErrorDTO.builder().errorMessage("GlobalExceptionHandler Error : " + errorMessage).build());
	}
}
