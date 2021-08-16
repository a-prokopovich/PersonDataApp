package com.prokopovich.persondata.service.exception;

public class PersonConstructorException extends RuntimeException {

    public PersonConstructorException(String reason) {
        super("Unable to construct Person, reason: " + reason);
    }

    public PersonConstructorException(String reason, Throwable cause) {
        super("Unable to construct Person, reason: " + reason, cause);
    }
}
