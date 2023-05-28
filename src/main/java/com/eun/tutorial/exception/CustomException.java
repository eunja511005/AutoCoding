package com.eun.tutorial.exception;

public class CustomException extends Exception {
	private static final long serialVersionUID = 9078360633501269211L;
	
	private int errorCode;

    public CustomException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
