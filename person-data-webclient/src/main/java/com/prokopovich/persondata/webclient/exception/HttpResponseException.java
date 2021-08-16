package com.prokopovich.persondata.webclient.exception;

public class HttpResponseException extends RuntimeException {

    public HttpResponseException(String reason) {
        super("HTTP response error, reason: " + reason);
    }
}
