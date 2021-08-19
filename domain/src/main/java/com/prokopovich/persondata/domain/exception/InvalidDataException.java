package com.prokopovich.persondata.domain.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String reason) {
        super("Invalid data, reason: " + reason);
    }
}
