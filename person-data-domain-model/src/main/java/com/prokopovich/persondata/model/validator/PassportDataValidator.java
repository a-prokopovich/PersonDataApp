package com.prokopovich.persondata.model.validator;

import com.prokopovich.persondata.model.entity.PassportData;
import com.prokopovich.persondata.model.exception.InvalidDataException;

public class PassportDataValidator {

    private final PassportNumberValidator passportNumberValidator = new PassportNumberValidator();
    private final RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

    public void validate(PassportData passportData) {
        if (requiredFieldValidator.containsUnfilledFields(passportData))  {
            throw new InvalidDataException("not all required fields are filled");
        }
        if (!passportNumberValidator.isValid(passportData.getPassportNumber())) {
            throw new InvalidDataException("invalid field with passportNumber");
        }
    }
}