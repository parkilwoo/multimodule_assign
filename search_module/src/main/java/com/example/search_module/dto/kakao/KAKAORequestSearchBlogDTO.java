package com.example.search_module.dto.kakao;

import com.example.search_module.dto.RequestSearchBlogDTO;
import com.example.search_module.validator.EnumValid;
import com.example.search_module.enums.KAKAOSorting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KAKAORequestSearchBlogDTO extends RequestSearchBlogDTO {
    private String query = super.query;
    @EnumValid(enumClass = KAKAOSorting.class, message = "sorting 값은 accuracy(정확도), recency(최신순)만 가능합니다.")
    private String sort;
    private Integer page;
    private Integer size;

    public KAKAORequestSearchBlogDTO() {
        setDefault();
    }

    @Override
    protected void setDefault() {
        if(sort == null) sort = KAKAOSorting.ACCURACY.getValue();
        if(page == null) page = 1;
        if(size == null) size = 10;
    }

    @Override
    public String getQueryParameter() {
        return super.fieldsToQueryParameter(this);
    }

    @Override
    public String getKeyword() {
        return splitQuery();
    }

    private String splitQuery() {
        String[] splitString = query.split(" ");
        if(splitString.length == 2) return splitString[1];
        return splitString[0];
    }
}
