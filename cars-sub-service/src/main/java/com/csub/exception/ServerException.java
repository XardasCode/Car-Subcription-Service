package com.csub.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {

    private final ErrorList errorCode;

    public ServerException(String message, ErrorList errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServerException(String message, Throwable cause, ErrorList errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
