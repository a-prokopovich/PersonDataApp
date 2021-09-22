package com.prokopovich.persondata.domain.validator.inurl;

import com.prokopovich.persondata.domain.exception.PersonServiceException;
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DefaultInUrlValidator implements ConstraintValidator<ValidInUrl, String> {

    private static final String[] SCHEMES = {"http", "https"};
    private static final UrlValidator URL_VALIDATOR = new UrlValidator(SCHEMES);

    @Override
    public void initialize(ValidInUrl constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {

        if (!URL_VALIDATOR.isValid(url)) throw new PersonServiceException("entered invalid URL", 400);
        else return true;
    }
}
