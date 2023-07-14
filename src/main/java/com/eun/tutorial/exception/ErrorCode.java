package com.eun.tutorial.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    EMAIL_DUPLICATION(400,"MEMBER-ERR-400","EMAIL DUPLICATED"),
    NO_AUTHORIZATION(403,"NO AUTHORIZATION-403","You don't have access permission to the resource."),
    ACCESS_DENIED(403,"SPRING-403","SPRING SECURITY CUSTOMFORBIDEN"),
    NO_COMMON_CODE(501,"NO_COMMON_CODE-501","Failed to search the common code data."),
    ;

    private int status;
    private String errorCode;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}