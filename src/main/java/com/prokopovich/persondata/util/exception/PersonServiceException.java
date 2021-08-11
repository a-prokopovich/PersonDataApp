package com.prokopovich.persondata.util.exception;

public class PersonServiceException extends RuntimeException {

    public PersonServiceException(String msg) {
        super("Unable to get Person from URL. " + msg);
    }

    public PersonServiceException(String reason, Throwable cause) {
        super("Unable to get Person from URL, reason: " + reason, cause);
    }
}
