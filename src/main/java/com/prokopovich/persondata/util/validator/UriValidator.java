package com.prokopovich.persondata.util.validator;

import org.apache.commons.validator.routines.UrlValidator;
import com.prokopovich.persondata.util.exception.InvalidUrlException;

public class UriValidator {

    public void checkEnteredUrl(String url) throws InvalidUrlException {
        String errorMsg = "Invalid URL: " + url;
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(url)) {
            throw new InvalidUrlException(errorMsg);
        }
    }
}
