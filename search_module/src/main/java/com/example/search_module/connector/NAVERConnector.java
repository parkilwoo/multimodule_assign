package com.example.search_module.connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NAVERConnector extends APIConnector{

    private final String URL = "https://openapi.naver.com/v1/search/blog.json";

    private final String X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    private final String CLIENT_ID = "jTERVrtFNmE3UN2istfB";

    private final String X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";
    private final String CLIENT_SECRET = "PUPhAJPci8";

    private String queryParameter;

    public NAVERConnector(String queryParameter) {
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
        Map<String, String> naverHeaderMap = new HashMap<>();
        naverHeaderMap.put(X_NAVER_CLIENT_ID, CLIENT_ID);
        naverHeaderMap.put(X_NAVER_CLIENT_SECRET, CLIENT_SECRET);
        super.requestHeader = naverHeaderMap;
    }
    public String connect() throws IOException {
        return super.getResponseBodyToString();
    }
}
