package com.example.search_module.dto;

import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public abstract class RequestSearchBlogDTO {

    @NotBlank(message = "질의어는 필수 파라미터 입니다.")
    protected String query;

    protected abstract void setDefault();
    public abstract String getQueryParameter();

    public abstract String getKeyword();
    private final StringBuilder stringBuilder = new StringBuilder();
    protected String fieldsToQueryParameter(Object dtoClass) {
        List<String> fieldList = new LinkedList<>();
        for (Field field : dtoClass.getClass().getDeclaredFields()) {
            fieldList.add(getFieldNameAndValue(field, dtoClass));
        }

        return String.join("&", fieldList);
    }

    private String getFieldNameAndValue(Field field, Object object) {
        stringBuilder.setLength(0);
        field.setAccessible(true);
        String fieldName = field.getName();
        Object fieldValue;
        String encodedFieldValue;
        try {
            fieldValue = field.get(object);
            encodedFieldValue = URLEncoder.encode(fieldValue.toString(), StandardCharsets.UTF_8);
        }
        catch (IllegalAccessException e) {
            log.error("{} 값에 접근을 실패하였습니다.", fieldName);
            return "";
        }
        stringBuilder.append(fieldName);
        stringBuilder.append("=");
        stringBuilder.append(encodedFieldValue);

        return stringBuilder.toString();
    }
}
