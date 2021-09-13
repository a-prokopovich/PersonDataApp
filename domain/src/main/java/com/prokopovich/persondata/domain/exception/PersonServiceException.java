package com.prokopovich.persondata.domain.exception;

public class PersonServiceException extends RuntimeException {

    private int statusCode;

    public PersonServiceException(String reason) {
        super(reason);
    }

    public PersonServiceException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public PersonServiceException(String reason, int statusCode) {
        super(reason);
        this.statusCode = statusCode;
    }

    public PersonServiceException(String reason, Throwable cause, int statusCode) {
        super(reason, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
