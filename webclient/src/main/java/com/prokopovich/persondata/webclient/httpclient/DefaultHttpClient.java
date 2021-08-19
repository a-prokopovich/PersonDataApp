package com.prokopovich.persondata.webclient.httpclient;

import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.exception.HttpClientException;
import com.prokopovich.persondata.webclient.response.HttpResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultHttpClient implements HttpClient {

    private static final Logger LOGGER = LogManager.getLogger(DefaultHttpClient.class);

    @Override
    public HttpResponse getData(String url) {
        LOGGER.trace("getData method is executed");

        int code;
        InputStream inputStream;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            LOGGER.debug("http connection opened");
            code = connection.getResponseCode();
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            throw new HttpClientException("connection error");
        }
        return new HttpResponse(code, inputStream);
    }
}
