package com.prokopovich.persondata.util.validator;

import com.prokopovich.persondata.util.exception.InvalidUrlException;
import org.apache.commons.validator.routines.UrlValidator;

public class DefaultEnteredUrlValidator implements EnteredUrlValidator {

    @Override
    public void checkEnteredUrl(String url) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(url)) {
            throw new InvalidUrlException(url);
        }
    }
}
