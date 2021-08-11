package com.prokopovich.persondata.util.parser.exception;

public class ParserException extends RuntimeException {

    public ParserException(String reason) {
            super("Unable to parse the InputStream: " + reason);
    }
}
