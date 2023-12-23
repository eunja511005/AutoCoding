package com.eun.tutorial.api.v1.exception;

/**
 * 
 * @author ywbest.park
 * 
 * GlobalExceptionHandler에서 공통으로 예외 발생시 오류 메세지를 리턴해 주지만
 * 세부적으로 ApiErrorCode에 따른 메세지를 클라이언트에 제공 하고 싶을때는
 * CustomException을 이용하여 메세지 전달
 *
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 8069631968744024775L;
    private final String errorCode;

    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}