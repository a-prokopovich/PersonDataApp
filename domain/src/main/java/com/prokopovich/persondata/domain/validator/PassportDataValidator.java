package com.prokopovich.persondata.domain.validator;

import com.prokopovich.persondata.domain.model.PassportData;
import com.prokopovich.persondata.domain.exception.InvalidDataException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PassportDataValidator {

    private final PassportNumberValidator passportNumberValidator;
    private final RequiredFieldValidator requiredFieldValidator;

    public void validate(PassportData passportData) {
        if (requiredFieldValidator.containsUnfilledFields(passportData))  {
            throw new InvalidDataException("not all required fields are filled");
        }
        if (!passportNumberValidator.isValid(passportData.getPassportNumber())) {
            throw new InvalidDataException("invalid field with passportNumber");
        }
    }
}
