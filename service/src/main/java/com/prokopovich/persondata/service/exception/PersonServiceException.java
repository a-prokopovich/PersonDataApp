package com.prokopovich.persondata.service.exception;

public class PersonServiceException extends RuntimeException {

    public PersonServiceException(String reason) {
        super("Unable to get Person from URL: " + reason);
    }

    public PersonServiceException(String reason, Throwable cause) {
        super("Unable to get Person from URL: " + reason, cause);
    }
}
