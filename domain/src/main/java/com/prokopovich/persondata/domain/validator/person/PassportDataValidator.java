package com.prokopovich.persondata.domain.validator.person;

import com.prokopovich.persondata.domain.model.PassportData;
import com.prokopovich.persondata.domain.exception.InvalidDataException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PassportDataValidator {

    private final PassportNumberValidator passportNumberValidator;
    private final RequiredFieldValidator requiredFieldValidator;

    public PassportDataValidator() {
        passportNumberValidator = new PassportNumberValidator();
        requiredFieldValidator = new RequiredFieldValidator();
    }

    public void validate(PassportData passportData) {
        if (requiredFieldValidator.containsUnfilledFields(passportData))  {
            throw new InvalidDataException("not all required fields are filled");
        }
        if (!passportNumberValidator.isValid(passportData.getPassportNumber())) {
            throw new InvalidDataException("invalid field with passportNumber");
        }
    }
}
