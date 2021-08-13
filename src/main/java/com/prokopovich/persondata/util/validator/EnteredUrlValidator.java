package com.prokopovich.persondata.util.validator;

public interface EnteredUrlValidator {

    /**
     * @throws InvalidUrlException if URL not valid
     */
    boolean checkEnteredUrl(String url);
}
