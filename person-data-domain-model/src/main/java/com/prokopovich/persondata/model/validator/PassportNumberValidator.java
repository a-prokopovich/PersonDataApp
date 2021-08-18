package com.prokopovich.persondata.model.validator;

public class PassportNumberValidator {

    public boolean isValid(String passportNumber) {
        return passportNumber.matches("[A-Z0-9]{2}[\\u0020]?[0-9]{2}[\\u0020]?[0-9]{2,8}");
    }
}
