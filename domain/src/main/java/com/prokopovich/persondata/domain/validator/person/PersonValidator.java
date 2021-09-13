package com.prokopovich.persondata.domain.validator.person;

import com.prokopovich.persondata.domain.exception.InvalidDataException;
import com.prokopovich.persondata.domain.model.Person;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersonValidator {

    private final PhoneValidator phoneValidator;
    private final EmailValidator emailValidator;
    private final PassportDataValidator passportDataValidator;
    private final RequiredFieldValidator requiredFieldValidator;

    public PersonValidator() {
        phoneValidator = new PhoneValidator();
        emailValidator = new EmailValidator();
        passportDataValidator = new PassportDataValidator();
        requiredFieldValidator = new RequiredFieldValidator();
    }

    public void validate(Person person) {
        if (requiredFieldValidator.containsUnfilledFields(person))  {
            throw new InvalidDataException("not all required fields are filled");
        }
        if (!phoneValidator.isValid(person.getPhone())) {
            throw new InvalidDataException("invalid field with phone number");
        }
        if (!emailValidator.isValid(person.getEmail())) {
            throw new InvalidDataException("invalid field with email");
        }
        if (person.getPassportData() != null) {
            passportDataValidator.validate(person.getPassportData());
        }
    }
}
