package com.csub.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CarSearchInfo {
    private List<String> filter;

    private String sortField;

    private String direction;

    private int page;
    
    private int size;
}
