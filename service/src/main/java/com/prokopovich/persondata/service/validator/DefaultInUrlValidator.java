package com.prokopovich.persondata.service.validator;

import org.apache.commons.validator.routines.UrlValidator;

public class DefaultInUrlValidator implements InUrlValidator {

    static String[] schemes = {"http", "https"};
    static UrlValidator urlValidator = new UrlValidator(schemes);

    @Override
    public boolean checkEnteredUrl(String url) {
        return urlValidator.isValid(url);
    }
}
