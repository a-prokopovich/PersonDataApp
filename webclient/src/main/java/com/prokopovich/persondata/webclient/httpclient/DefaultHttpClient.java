package com.prokopovich.persondata.webclient.httpclient;

import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.exception.HttpClientException;
import com.prokopovich.persondata.webclient.response.HttpResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class DefaultHttpClient implements HttpClient {

    @Override
    public HttpResponse getData(String url) {

        log.debug("Downloading content by url, {}", url);

        try {
            var connection = (HttpURLConnection) new URL(url).openConnection();
            var response = HttpResponse.builder()
                .statusCode(connection.getResponseCode())
                .body(IOUtils.toByteArray((connection.getInputStream())))
                .build();

            log.debug("Got httpResponse: {}", response);
            return response;

        } catch (IOException e) {
            throw new HttpClientException("connection error", e);
        }
    }
}
