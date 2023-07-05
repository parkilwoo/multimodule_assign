package com.example.search_module.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Document {
    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private Date datetime;
}
