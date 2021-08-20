package com.prokopovich.persondata.domain.exception;

public class PersonConstructorException extends RuntimeException {

    public PersonConstructorException(String reason) {
        super("unable to construct Person, reason: " + reason);
    }

    public PersonConstructorException(String reason, Throwable cause) {
        super("unable to construct Person, reason: " + reason, cause);
    }
}
