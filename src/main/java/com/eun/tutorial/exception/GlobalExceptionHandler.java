package com.eun.tutorial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eun.tutorial.dto.main.ApiResponse;
import com.eun.tutorial.util.ExceptionUtils;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

	private final ExceptionUtils exceptionUtils;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<ErrorCode>> handleException(Exception e) {

		if (e instanceof CustomException) {
			CustomException customEx = (CustomException) e;
			int errorCode = customEx.getErrorCode();
			e = customEx.getException();
			
			e.printStackTrace();
			exceptionUtils.saveErrorLog(org.apache.tika.utils.ExceptionUtils.getStackTrace(e.getCause()));
			
			if (errorCode == 403) {
				ApiResponse<ErrorCode> apiResponse = new ApiResponse<>(false, ErrorCode.NO_AUTHORIZATION.getMessage(),
						ErrorCode.NO_AUTHORIZATION);
				return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
			}
		} else {
			e.printStackTrace();
			exceptionUtils.saveErrorLog(org.apache.tika.utils.ExceptionUtils.getStackTrace(e));
		}

		ApiResponse<ErrorCode> apiResponse = new ApiResponse<>(false, ErrorCode.INTER_SERVER_ERROR.getMessage(),
				ErrorCode.INTER_SERVER_ERROR);
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
