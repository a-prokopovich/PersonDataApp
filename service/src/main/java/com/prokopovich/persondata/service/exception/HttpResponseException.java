package com.prokopovich.persondata.service.exception;

public class HttpResponseException extends RuntimeException {

    public HttpResponseException(String reason) {
        super("HTTP response error: " + reason);
    }
}
