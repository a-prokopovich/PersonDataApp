package com.prokopovich.persondata.service.validator;

import org.apache.commons.validator.routines.UrlValidator;

public class DefaultInUrlValidator implements InUrlValidator {

    private static final String[] SCHEMES = {"http", "https"};
    private static final UrlValidator URL_VALIDATOR = new UrlValidator(SCHEMES);

    @Override
    public boolean checkEnteredUrl(String url) {
        return URL_VALIDATOR.isValid(url);
    }
}
