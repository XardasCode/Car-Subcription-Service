package com.csub.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionJSONInfo> handleException(Exception e) {
        log.error("Internal server error: " + e.getMessage());
        return new ResponseEntity<>(
                new ExceptionJSONInfo("Internal server error", e.getMessage(), ErrorCode.SERVER_ERROR.getErrorCode()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionJSONInfo> handleException(UserNotFoundException e) {
        log.error("User not found error: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionJSONInfo(
                "User not found error",
                e.getMessage(),
                ErrorCode.USER_NOT_FOUND.getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionJSONInfo> handleException(UserAlreadyExistsException e) {
        log.error("User already exists error: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionJSONInfo(
                "User already exists error",
                e.getMessage(),
                ErrorCode.USER_ALREADY_EXISTS.getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<ExceptionJSONInfo> handleException(ManagerNotFoundException e) {
        log.error("Manager not found error: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionJSONInfo(
                "Manager not found error",
                e.getMessage(),
                ErrorCode.MANAGER_NOT_FOUND.getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ManagerAlreadyExistsException.class)
    public ResponseEntity<ExceptionJSONInfo> handleException(ManagerAlreadyExistsException e) {
        log.error("Manager already exists error: " + e.getMessage());
        return new ResponseEntity<>(new ExceptionJSONInfo(
                "Manager already exists error",
                e.getMessage(),
                ErrorCode.MANAGER_ALREADY_EXISTS.getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

}
