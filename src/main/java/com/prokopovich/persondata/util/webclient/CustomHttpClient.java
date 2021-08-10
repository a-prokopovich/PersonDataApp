package com.prokopovich.persondata.util.webclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomHttpClient implements CustomClient {

    @Override
    public CustomResponse getData(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int code = connection.getResponseCode();
        InputStream inputStream = connection.getInputStream();
        return new CustomResponse(code, inputStream);
    }
}
