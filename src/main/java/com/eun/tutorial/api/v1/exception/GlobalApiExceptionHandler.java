package com.eun.tutorial.api.v1.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eun.tutorial.api.v1.dto.ApiResponse;


/**
 * Controller에서 예외 발생시 공통으로 처리
 * throws Exception 안해도 ControllerAdvice 어노테이션을 사용했기 때문에 처리 됨
 * Controller에서 try-catch 사용할때는 GlobalExceptionHandler 동작 안함
 * 500 인터널 오류 말고 세부적인 오류 관리가 필요 할때는 try-catch 사용
 * try-catch 사용할때는 CustomException 이용하여 메세지 세분화 가능
 * ResponseEntity로 ApiResponse를 한번 더 감싸주는 주면 HttpReturn Code를 포함하여 응답 가능 하므로 Client에서 관리 용이 
 */
@ControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(ApiErrorCode.INTERNAL_SERVER_ERROR));
    }
    
    /**
     * Spring에서 제공하는 Bean Validation을 사용하는 경우
     * 유효성 검사에 실패한 경우 MethodArgumentNotValidException 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        String errorMessage = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiResponse<Object> response = ApiResponse.failure(ApiErrorCode.INVALID_REQUEST.getCode(), "Validation error: " + errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

