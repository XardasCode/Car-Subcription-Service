package com.csub.exception;

public enum ErrorCode {
    USER_NOT_FOUND(1001),
    SUBSCRIPTION_NOT_FOUND(1002),
    MANAGER_NOT_FOUND(1003),
    USER_ALREADY_EXISTS(1004),
    SUBSCRIPTION_ALREADY_EXISTS(1005),
    MANAGER_ALREADY_EXISTS(1006),
    USER_NOT_VALID(1007),
    SUBSCRIPTION_NOT_VALID(1008),
    MANAGER_NOT_VALID(1009),
    USER_NOT_AUTHORIZED(1010),
    SUBSCRIPTION_NOT_AUTHORIZED(1011),
    MANAGER_NOT_AUTHORIZED(1012),
    USER_NOT_LOGGED_IN(1013),
    SUBSCRIPTION_NOT_LOGGED_IN(1014),
    MANAGER_NOT_LOGGED_IN(1015),
    USER_NOT_CREATED(1016),
    SUBSCRIPTION_NOT_CREATED(1017),
    MANAGER_NOT_CREATED(1018),

    SERVER_ERROR(2000);

    private int code;

    ErrorCode(int errorCode) {
        this.code = errorCode;
    }

    public int getErrorCode() {
        return code;
    }

    public void setErrorCode(int errorCode) {
        this.code = errorCode;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
