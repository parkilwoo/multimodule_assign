package com.example.search_module.dto;

import com.example.search_module.vo.Documents;
import com.example.search_module.vo.Meta;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public abstract class ResponseSearchBlogDTO {
    protected Meta meta;

    protected Documents documents;

    public abstract void parseDTO();

    protected abstract void parseResponseBodyToDTO();

    protected JsonElement convertStringToJsonElement(String jsonString) {
        return JsonParser.parseString(jsonString);
    }

}
