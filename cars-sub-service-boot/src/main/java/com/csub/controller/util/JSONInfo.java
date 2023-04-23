package com.csub.controller.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class JSONInfo {

    private String status;

    private String message;

}
