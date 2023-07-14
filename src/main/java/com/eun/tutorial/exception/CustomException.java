package com.eun.tutorial.exception;

public class CustomException extends Exception {
	private static final long serialVersionUID = 9078360633501269211L;
	
	private final int errorCode;
	private final Exception exception;

    public CustomException(ErrorCode errorCode, Exception exception) {
        this.errorCode = errorCode.getStatus();
        this.exception = exception;
    }

    public int getErrorCode() {
        return errorCode;
    }
    
    public Exception getException() {
    	return exception;
    }
}
