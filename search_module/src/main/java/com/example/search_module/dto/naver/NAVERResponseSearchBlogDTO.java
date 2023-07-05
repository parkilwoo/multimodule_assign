package com.example.search_module.dto.naver;

import com.example.search_module.dto.ResponseSearchBlogDTO;
import com.example.search_module.vo.Document;
import com.example.search_module.vo.Documents;
import com.example.search_module.vo.Meta;
import com.example.search_module.vo.naver.Item;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class NAVERResponseSearchBlogDTO extends ResponseSearchBlogDTO {

    private transient String jsonString;

    public NAVERResponseSearchBlogDTO(String jsonString) {
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

        Item[] items = gson.fromJson(jsonElement.getAsJsonObject().get("items"), Item[].class);
        Integer totalCount = gson.fromJson(jsonElement.getAsJsonObject().get("total"), Integer.class);
        Integer start = gson.fromJson(jsonElement.getAsJsonObject().get("start"), Integer.class);
        Integer display = gson.fromJson(jsonElement.getAsJsonObject().get("display"), Integer.class);
        try {
            documents = new Documents(itemsConvertToDocuments(items));
            meta = parseToMeta(totalCount, start, display);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private Document[] itemsConvertToDocuments(Item[] items) throws ParseException {
        Document[] documentArray = new Document[items.length];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
        for (int i = 0; i < documentArray.length; i++) {
            documentArray[i] = Document.builder()
                    .title(items[i].getTitle())
                    .contents(items[i].getDescription())
                    .url(items[i].getLink())
                    .blogname(items[i].getBloggername())
                    .datetime(dateFormat.parse(items[i].getPostdate()))
                    .build();
        }
        return documentArray;
    }

    private Meta parseToMeta(Integer totalCount, Integer start, Integer display) {
        boolean is_end = (start * display >= totalCount) ? true : false;
        return Meta.builder()
                .total_count(totalCount)
                .pageable_count(totalCount)
                .is_end(is_end)
                .build();
    }

    public Meta getMeta() {
        return super.meta;
    }

    public List<Document> getDocuments() {
        return super.documents.getDocuments();
    }

}
