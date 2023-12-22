package com.eun.tutorial.api.v1.exception;

/**
 * 
 * @author ywbest.park
 * 
 * API의 에러코드를 공통으로 관리하기 위한 Enum 클래스
 * 정상/에러 코드는 정수로 관리하고 에러 메세지는 문자열로 관리
 *
 */
public enum ApiErrorCode {
    // 성공 코드
    SUCCESS(0, "API successful"),
    
    // 요청 관련 에러
    INVALID_REQUEST(1000, "Invalid request parameters"),
    MISSING_PARAMETER(1001, "Missing required parameter"),
    INVALID_FORMAT(1002, "Invalid format of data"),
    UNSUPPORTED_OPERATION(1003, "Unsupported operation"),

    // 인증 및 권한 관련 에러
    UNAUTHORIZED_ACCESS(2000, "Unauthorized access"),
    FORBIDDEN(2001, "Access forbidden"),
    TOKEN_EXPIRED(2002, "Token has expired"),
    INVALID_CREDENTIALS(2003, "Invalid credentials"),

    // 데이터 관련 에러
    DATA_NOT_FOUND(3000, "Data not found"),
    DUPLICATE_ENTRY(3001, "Duplicate entry exists"),
    DATA_CONFLICT(3002, "Data conflict error"),
    DATA_TOO_LARGE(3003, "Data size too large"),

    // 서버 내부 에러
    INTERNAL_SERVER_ERROR(5000, "Internal server error"),
    SERVICE_UNAVAILABLE(5001, "Service unavailable"),
    DATABASE_ERROR(5002, "Database error"),
    NETWORK_ERROR(5003, "Network error"),

    // 기타 에러
    UNKNOWN_ERROR(9999, "Unknown error occurred");

    private final int code;
    private final String message;

    ApiErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
