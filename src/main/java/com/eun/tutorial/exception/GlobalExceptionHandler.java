package com.eun.tutorial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.service.ZthhErrorService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final ZthhErrorService zthhErrorService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("handleException",ex);
        
        
        String errorMessage = org.apache.tika.utils.ExceptionUtils.getStackTrace(ex);

        if(errorMessage.length()>2000) {
        	errorMessage = errorMessage.substring(0, 2000);
        }
        
        zthhErrorService.save(ZthhErrorDTO.builder()
                                .errorMessage("GlobalExceptionHandler Error : " + errorMessage)
                                .build()
        );
        ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
