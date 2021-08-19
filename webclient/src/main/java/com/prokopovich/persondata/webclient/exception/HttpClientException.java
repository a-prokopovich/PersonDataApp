package com.prokopovich.persondata.webclient.exception;

public class HttpClientException extends RuntimeException {

    public HttpClientException(String reason) {
        super("HTTP error, reason: " + reason);
    }
}
