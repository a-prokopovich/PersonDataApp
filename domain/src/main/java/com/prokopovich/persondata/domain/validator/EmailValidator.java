package com.prokopovich.persondata.domain.validator;

public class EmailValidator {

    public boolean isValid(String email) {
        return email == null
                || email.equals("")
                || email.matches("[.\\-_a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+");
    }
}
