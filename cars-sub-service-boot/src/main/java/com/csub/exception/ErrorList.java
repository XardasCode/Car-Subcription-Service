package com.csub.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorList {
    USER_NOT_FOUND(1001, HttpStatus.NOT_FOUND),
    SUBSCRIPTION_NOT_FOUND(1002, HttpStatus.NOT_FOUND),
    MANAGER_NOT_FOUND(1003, HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1004, HttpStatus.NOT_FOUND),
    SUBSCRIPTION_ALREADY_EXISTS(1005, HttpStatus.NOT_MODIFIED),
    MANAGER_ALREADY_EXISTS(1006, HttpStatus.NOT_MODIFIED),
    USER_NOT_VALID(1007, HttpStatus.BAD_REQUEST),
    SUBSCRIPTION_NOT_VALID(1008, HttpStatus.BAD_REQUEST),
    MANAGER_NOT_VALID(1009, HttpStatus.BAD_REQUEST),
    USER_NOT_AUTHORIZED(1010, HttpStatus.UNAUTHORIZED),
    SUBSCRIPTION_NOT_AUTHORIZED(1011, HttpStatus.UNAUTHORIZED),
    MANAGER_NOT_AUTHORIZED(1012, HttpStatus.UNAUTHORIZED),
    USER_NOT_LOGGED_IN(1013, HttpStatus.UNAUTHORIZED),
    SUBSCRIPTION_NOT_LOGGED_IN(1014, HttpStatus.UNAUTHORIZED),
    MANAGER_NOT_LOGGED_IN(1015, HttpStatus.UNAUTHORIZED),
    USER_NOT_CREATED(1016, HttpStatus.NOT_MODIFIED),
    SUBSCRIPTION_NOT_CREATED(1017, HttpStatus.NOT_MODIFIED),
    MANAGER_NOT_CREATED(1018, HttpStatus.NOT_MODIFIED),

    VALIDATION_ERROR(1019, HttpStatus.BAD_REQUEST),

    CAR_NOT_FOUND(1020, HttpStatus.NOT_FOUND),

    SERVER_ERROR(2000, HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_SAVE_ERROR(2001, HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_GET_ERROR(2002, HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;

    private final HttpStatus status;

    public int getErrorCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return status;
    }

}
