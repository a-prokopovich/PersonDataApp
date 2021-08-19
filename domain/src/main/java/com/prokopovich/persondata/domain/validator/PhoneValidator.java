package com.prokopovich.persondata.domain.validator;

public class PhoneValidator {

    public boolean isValid(String phone) {
        return phone == null
                || phone.equals("")
                || phone.matches("\\+?[0-9\\-\\(\\)]+");
    }
}
