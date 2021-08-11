package com.prokopovich.persondata.webclient;

import com.prokopovich.persondata.webclient.exception.HttpClientException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultHttpClient implements HttpClient {

    @Override
    public HttpResponse getData(URL url) {
        int code;
        InputStream inputStream;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            code = connection.getResponseCode();
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            throw new HttpClientException("connection error");
        }
        return new HttpResponse(code, inputStream);
    }
}
