package com.csub.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExceptionJSONInfo {

    private String error;

    private String errorMessage;

    private int code;
}
