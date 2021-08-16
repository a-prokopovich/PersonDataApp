package com.prokopovich.persondata.webclient.httpclient;

import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.exception.HttpClientException;
import com.prokopovich.persondata.webclient.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultHttpClient implements HttpClient {

    @Override
    public HttpResponse getData(String url) {
        int code;
        InputStream inputStream;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            code = connection.getResponseCode();
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            throw new HttpClientException("connection error");
        }
        return new HttpResponse(code, inputStream);
    }
}
