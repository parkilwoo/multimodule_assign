package com.example.search_module.dto.naver;

import com.example.search_module.dto.RequestSearchBlogDTO;
import com.example.search_module.dto.kakao.KAKAORequestSearchBlogDTO;
import com.example.search_module.enums.KAKAOSorting;
import com.example.search_module.enums.NAVERSorting;
import com.example.search_module.validator.EnumValid;

public class NAVERRequestSearchBlogDTO extends RequestSearchBlogDTO {
    private String query = super.query;
    @EnumValid(enumClass = NAVERSorting.class, message = "sorting 값은 sim(정확도), date(날짜순)만 가능합니다.")
    private String sort;
    private Integer start;
    private Integer display;

    public NAVERRequestSearchBlogDTO() {
        setDefault();
    }
    @Override
    protected void setDefault() {
        if(sort == null) sort = NAVERSorting.SIM.getValue();
        if(start == null) start = 1;
        if(display == null) display = 10;
    }

    @Override
    public String getQueryParameter() {
        return super.fieldsToQueryParameter(this);
    }

    @Override
    public String getKeyword() {
        return null;
    }

    public void convertKAKAORequestDtoToNAVERRequestDTO(KAKAORequestSearchBlogDTO kakaoRequestSearchBlogDTO) {
        this.query = kakaoRequestSearchBlogDTO.getQuery();
        String kakaoSort = kakaoRequestSearchBlogDTO.getSort();
        if(kakaoSort.equals(KAKAOSorting.ACCURACY.getValue())) this.sort = NAVERSorting.SIM.getValue();
        else this.sort = NAVERSorting.DATE.getValue();

        this.start = kakaoRequestSearchBlogDTO.getPage();
        this.display = kakaoRequestSearchBlogDTO.getSize();
    }
}
