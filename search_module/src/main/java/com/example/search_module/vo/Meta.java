package com.example.search_module.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Meta {
    private int total_count;
    private int pageable_count;
    private boolean is_end;
}
