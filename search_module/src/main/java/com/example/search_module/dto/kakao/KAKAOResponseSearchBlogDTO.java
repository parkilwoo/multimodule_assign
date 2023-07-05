package com.example.search_module.dto.kakao;
import com.example.search_module.dto.ResponseSearchBlogDTO;
import com.example.search_module.vo.Document;
import com.example.search_module.vo.Documents;
import com.example.search_module.vo.Meta;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.List;

public class KAKAOResponseSearchBlogDTO extends ResponseSearchBlogDTO {

    private transient String jsonString;

    public KAKAOResponseSearchBlogDTO(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public void parseDTO() {
        parseResponseBodyToDTO();
    }

    @Override
    protected void parseResponseBodyToDTO() {
        Gson gson = new Gson();
        JsonElement jsonElement = super.convertStringToJsonElement(jsonString);
        meta = gson.fromJson(jsonElement.getAsJsonObject().get("meta"), Meta.class);
        documents = new Documents(gson.fromJson(jsonElement.getAsJsonObject().get("documents"), Document[].class));
    }

    public Meta getMeta() {
        return super.meta;
    }

    public List<Document> getDocuments() {
        return super.documents.getDocuments();
    }
}
