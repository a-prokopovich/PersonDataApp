package com.prokopovich.persondata.domain.exception;

public class HttpResponseException extends RuntimeException {

    public HttpResponseException(String reason) {
        super("HTTP response error: " + reason);
    }
}
