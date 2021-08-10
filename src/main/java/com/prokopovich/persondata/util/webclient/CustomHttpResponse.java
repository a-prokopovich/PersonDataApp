package com.prokopovich.persondata.util.webclient;

import java.io.InputStream;

public class CustomHttpResponse {
    private int statusCode;
    private InputStream body;

    public CustomHttpResponse(int statusCode, InputStream body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public InputStream getBody() {
        return body;
    }

    public void setBody(InputStream body) {
        this.body = body;
    }
}
