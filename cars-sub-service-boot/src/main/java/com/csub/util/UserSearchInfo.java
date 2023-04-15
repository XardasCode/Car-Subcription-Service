package com.csub.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSearchInfo {
    private int page;
    private int size;
}
