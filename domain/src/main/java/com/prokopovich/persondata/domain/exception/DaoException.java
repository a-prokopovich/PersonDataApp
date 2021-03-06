package com.prokopovich.persondata.domain.exception;

public class DaoException extends RuntimeException {

    public DaoException() { }

    public DaoException(String reason) {
        super(reason);
    }

    public DaoException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
