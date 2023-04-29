package com.csub.controller.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class JSONInfo {

    @Builder.Default
    private String status = "success";

    private String message;

}
