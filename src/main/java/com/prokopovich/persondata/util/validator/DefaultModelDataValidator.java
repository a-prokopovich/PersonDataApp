package com.prokopovich.persondata.util.validator;

import com.prokopovich.persondata.util.exception.InvalidDataException;
import com.prokopovich.persondata.model.PassportData;
import com.prokopovich.persondata.model.Person;

public class DefaultModelDataValidator implements ModelDataValidator {

    @Override
    public void checkPersonData(Person person) {
        if (person.getId() == 0 ||person.getFullName() == null)  {
            throw new InvalidDataException("not all required fields are filled");
        }
        if (person.getPhone() != null &&
                !person.getPhone().matches("\\+?[0-9\\-\\(\\)]+")) {
            throw new InvalidDataException("invalid field phone");
        }
        if (person.getEmail() != null &&
                !person.getEmail().matches("[\\-_a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+")) {
            throw new InvalidDataException("invalid field email");
        }
        if (person.getPassportData() != null) {
            if (person.getPassportData().getPersonId() == 0 ||
                    person.getId() != person.getPassportData().getPersonId()) {
                throw new InvalidDataException("invalid field Passport data (id don't match)");
            }
            checkPassportData(person.getPassportData());
        }
    }

    @Override
    public void checkPassportData(PassportData passportData) {
        if (passportData.getPersonId() == 0 ||
                passportData.getPassportNumber() == null ||
                passportData.getIdentificationNumber() == null)  {
            throw new InvalidDataException("not all required fields are filled");
        }
        if (!passportData.getPassportNumber().matches("[A-Z0-9\\u0020]{6,12}")) {
            throw new InvalidDataException("invalid passportNumber");
        }
    }
}
