package com.example.search_module.vo;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class Documents {

    private List<Document> documents;

    public Documents(Document[] documentArray) {
        documents = Arrays.asList(documentArray);
    }
}
