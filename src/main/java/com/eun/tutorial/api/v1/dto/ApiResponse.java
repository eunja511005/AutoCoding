package com.eun.tutorial.api.v1.dto;

import com.eun.tutorial.api.v1.exception.ApiErrorCode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private int errorCode;
    private String errorMessage;

    public ApiResponse(boolean success, T data, int errorCode, String errorMessage) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 
     * @param <T>
     * @param data
     * @return
     * 성공 메세지 리턴을 위한 생성자
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMessage());
    }

    /**
     * 
     * @param <T>
     * @param errorCode
     * @param errorMessage
     * @return
     * 일반적인 실패 리턴을 위한 생성자
     */
    public static <T> ApiResponse<T> failure(ApiErrorCode apiErrorCode) {
        return new ApiResponse<>(false, null, apiErrorCode.getCode(), apiErrorCode.getMessage());
    }
    
    /**
     * 
     * @param <T>
     * @param errorCode
     * @param errorMessage
     * @return
     * ApiErrorCode로는 개별 상세 오류 내용까지는 보낼 수 없어서 생성자 추가
     */
    public static <T> ApiResponse<T> failure(int errorCode, String errorMessage) {
        return new ApiResponse<>(false, null, errorCode, errorMessage);
    }
}
