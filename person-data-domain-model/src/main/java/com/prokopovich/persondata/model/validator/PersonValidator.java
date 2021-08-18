package com.prokopovich.persondata.model.validator;

import com.prokopovich.persondata.model.exception.InvalidDataException;
import com.prokopovich.persondata.model.entity.Person;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonValidator {

    private final PhoneValidator phoneValidator;
    private final EmailValidator emailValidator;
    private final PassportDataValidator passportDataValidator;
    private final RequiredFieldValidator requiredFieldValidator;

    public PersonValidator() {
        this.phoneValidator = new PhoneValidator();
        this.emailValidator = new EmailValidator();
        this.passportDataValidator = new PassportDataValidator();
        this.requiredFieldValidator = new RequiredFieldValidator();
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
