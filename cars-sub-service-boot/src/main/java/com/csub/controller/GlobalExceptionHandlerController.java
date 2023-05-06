package com.csub.controller;

import com.csub.exception.ErrorList;
import com.csub.exception.ExceptionJSONInfo;
import com.csub.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionJSONInfo> handleException(Exception e) {
        log.error("Internal server error: " + e.getMessage());
        return new ResponseEntity<>(
                new ExceptionJSONInfo("Internal server error", e.getMessage(), ErrorList.SERVER_ERROR.getErrorCode()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ExceptionJSONInfo> handleServerException(ServerException e) {
        log.error("Server exception: " + e.getMessage());
        log.error("Error code: " + e.getErrorCode().getErrorCode());
        return new ResponseEntity<>(
                new ExceptionJSONInfo("Error", e.getMessage(), e.getErrorCode().getErrorCode()),
                e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionJSONInfo> handleException(MethodArgumentNotValidException exception) {
        log.error("Exception: ", exception);
        log.error("Exception type: {}", exception.getClass().getName());
        log.error("Exception message: {}", exception.getMessage());
        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        exception.getBindingResult().getGlobalErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionJSONInfo
                        .builder()
                        .errorMessage(errors.toString())
                        .code(ErrorList.VALIDATION_ERROR.getErrorCode())
                        .build());
    }
}
