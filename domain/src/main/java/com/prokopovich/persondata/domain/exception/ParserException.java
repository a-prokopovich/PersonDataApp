package com.prokopovich.persondata.domain.exception;

public class ParserException extends RuntimeException {

    public ParserException(String reason) {
            super("unable to parse the byte array: " + reason);
    }
}
