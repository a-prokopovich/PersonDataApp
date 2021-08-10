package com.prokopovich.persondata.util.exception;

import java.io.IOException;

public class InvalidUrlException extends IOException {

    public InvalidUrlException(String message) {
        super(message);
    }
}