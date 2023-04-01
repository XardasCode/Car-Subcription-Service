package com.csub.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionJSONInfo {
    private String error;
    private String errorMessage;
    private int errorCode;
}
