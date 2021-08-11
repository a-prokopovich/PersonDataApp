package com.prokopovich.persondata.util.exception;

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String url) {
        super("Invalid Url: " + url);
    }
}