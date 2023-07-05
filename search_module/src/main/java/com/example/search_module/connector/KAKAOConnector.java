package com.example.search_module.connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KAKAOConnector extends APIConnector{

    private final String URL = "https://dapi.kakao.com/v2/search/blog";
    private final String REST_API_KEY = "KakaoAK 78104a257c68e0bbf2de24556879b226";
    private final String AUTHORIZATION = "Authorization";

    private String queryParameter;

    public KAKAOConnector(String queryParameter) {
        this.queryParameter = queryParameter;
        setApiUrl();
        setRequestHeader();
    }
    @Override
    void setApiUrl() {
        super.apiUrl = URL + "?" + queryParameter;
    }

    @Override
    void setRequestHeader() {
        Map<String, String> kakaoHeaderMap = new HashMap<>();
        kakaoHeaderMap.put(AUTHORIZATION, REST_API_KEY);
        super.requestHeader = kakaoHeaderMap;
    }

    public String connect() throws IOException {
        return super.getResponseBodyToString();
    }
}
