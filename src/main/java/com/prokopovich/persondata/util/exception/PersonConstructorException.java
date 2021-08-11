package com.prokopovich.persondata.util.exception;

public class PersonConstructorException extends RuntimeException {

    public PersonConstructorException(String reason, Throwable cause) {
        super("Unable to construct Person, reason: " + reason, cause);
    }
}
