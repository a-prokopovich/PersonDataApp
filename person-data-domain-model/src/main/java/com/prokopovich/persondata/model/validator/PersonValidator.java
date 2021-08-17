package com.prokopovich.persondata.model.validator;

import com.prokopovich.persondata.model.exception.InvalidDataException;
import com.prokopovich.persondata.model.entity.Person;

public class PersonValidator {

    private final PhoneValidator phoneValidator = new PhoneValidator();
    private final EmailValidator emailValidator = new EmailValidator();
    private final PassportDataValidator passportDataValidator = new PassportDataValidator();
    private final RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

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
            if (person.getPassportData().getPersonId() == 0 ||
                    person.getId() != person.getPassportData().getPersonId()) {
                throw new InvalidDataException("invalid field with Passport data (id don't match)");
            }
            passportDataValidator.validate(person.getPassportData());
        }
    }
}
