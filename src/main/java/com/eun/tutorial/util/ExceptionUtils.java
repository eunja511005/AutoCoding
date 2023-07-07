package com.eun.tutorial.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExceptionUtils {
    private ZthhErrorService zthhErrorService;

    @Autowired
    public ExceptionUtils(ZthhErrorService zthhErrorService) {
        this.zthhErrorService = zthhErrorService;
    }

    public void saveErrorLog(Exception exception) {
        String errorMessage = org.apache.tika.utils.ExceptionUtils.getStackTrace(exception);
        
        log.error(errorMessage);

        if (errorMessage.length() > 2000) {
            errorMessage = errorMessage.substring(0, 2000);
        }

        zthhErrorService.save(
                ZthhErrorDTO.builder().errorMessage("ExceptionUtils Error: " + errorMessage).build());

        exception.printStackTrace();
    }
}
