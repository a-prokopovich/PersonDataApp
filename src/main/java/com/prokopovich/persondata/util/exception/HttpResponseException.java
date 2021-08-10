package com.prokopovich.persondata.util.exception;

import java.io.IOException;

public class HttpResponseException extends IOException {

    public HttpResponseException(String message) {
        super(message);
    }
}
