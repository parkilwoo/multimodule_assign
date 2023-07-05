package com.example.search_module.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 외부 API 연동 Interface
 */
public abstract class APIConnector {

    protected String apiUrl;
    protected Map<String, String> requestHeader;

    abstract void setApiUrl();
    abstract void setRequestHeader();

    protected String getResponseBodyToString() throws IOException, MalformedURLException {
        HttpURLConnection connection = connect();
        try {
            connection.setRequestMethod("GET");
            for(Map.Entry<String, String> header : requestHeader.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) throw new IOException();

            return responseBodyToString(connection.getInputStream());
        } catch (IOException ioException) {
            throw new RuntimeException("API 요청과 응답 실패", ioException);
        } finally {
            connection.disconnect();
        }

    }

    private HttpURLConnection connect() throws IOException, MalformedURLException {
        try {
            URL url = new URL(this.apiUrl);
            return (HttpURLConnection) url.openConnection();
        }
        catch (MalformedURLException malformedURLException) {
            throw new MalformedURLException(String.format("%s 잘못된 URL 입니다.", this.apiUrl));
        }
        catch (IOException ioException) {
            throw new IOException(String.format("%s 연결이 실패 했습니다.", this.apiUrl), ioException);
        }
    }

    private String responseBodyToString(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } catch (IOException ioException) {
            throw new RuntimeException("responseBodyToString 실행에 에러가 발생하였습니다.", ioException);
        }
    }
}
