package com.example.search_module.vo.naver;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Item {
    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private String postdate;

}
