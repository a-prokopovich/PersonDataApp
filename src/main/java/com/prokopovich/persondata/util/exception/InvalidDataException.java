package com.prokopovich.persondata.util.exception;

import java.io.IOException;

public class InvalidDataException extends IOException {

    public InvalidDataException(String message) {
        super(message);
    }
}
