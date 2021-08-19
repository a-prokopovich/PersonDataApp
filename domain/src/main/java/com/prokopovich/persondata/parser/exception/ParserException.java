package com.prokopovich.persondata.parser.exception;

public class ParserException extends RuntimeException {

    public ParserException(String reason) {
            super("Unable to parse the byte array, reason: " + reason);
    }
}