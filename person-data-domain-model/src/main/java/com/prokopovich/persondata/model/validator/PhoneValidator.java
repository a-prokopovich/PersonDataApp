package com.prokopovich.persondata.model.validator;

public class PhoneValidator {

    public boolean isValid(String phone) {
        return phone == null
                || phone.equals("")
                || phone.matches("\\+?[0-9\\-\\(\\)]+");
    }
}
