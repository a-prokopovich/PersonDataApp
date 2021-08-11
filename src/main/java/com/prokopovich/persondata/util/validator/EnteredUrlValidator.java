package com.prokopovich.persondata.util.validator;

import com.prokopovich.persondata.util.exception.InvalidUrlException;

public interface EnteredUrlValidator {

    /**
     * @throws InvalidUrlException if URL not valid
     */
    void checkEnteredUrl(String url);
}
