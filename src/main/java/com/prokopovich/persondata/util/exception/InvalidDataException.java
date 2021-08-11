package com.prokopovich.persondata.util.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String reason) {
        super("Invalid data, reason: " + reason);
    }
}
