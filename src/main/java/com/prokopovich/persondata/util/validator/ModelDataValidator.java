package com.prokopovich.persondata.util.validator;

import com.prokopovich.persondata.util.exception.InvalidDataException;
import com.prokopovich.persondata.model.PassportData;
import com.prokopovich.persondata.model.Person;

public class ModelDataValidator {

    public void checkPersonData(Person person) throws InvalidDataException {
        String errorMsg = "Invalid Person data ";

        if (person.getId() == 0 ||person.getFullName() == null)  {
            throw new InvalidDataException(errorMsg + "(not all required fields are filled)");
        }
        if (person.getPhone() != null &&
                !person.getPhone().matches("\\+?[0-9\\-\\(\\)]+")) {
            throw new InvalidDataException(errorMsg + "(invalid phone)");
        }
        if (person.getEmail() != null &&
                !person.getEmail().matches("[\\-_a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+")) {
            throw new InvalidDataException(errorMsg + "(invalid email)");
        }
        if (person.getPassportData() != null) {
            if (person.getPassportData().getPersonId() == 0 ||
                    person.getId() != person.getPassportData().getPersonId()) {
                throw new InvalidDataException(errorMsg + "(invalid Passport data)");
            }
            checkPassportData(person.getPassportData());
        }
    }

    public void checkPassportData(PassportData passportData) throws InvalidDataException {
        String errorMsg = "Invalid Passport data ";

        if (passportData.getPersonId() == 0 ||
                passportData.getPassportNumber() == null ||
                passportData.getIdentificationNumber() == null)  {
            throw new InvalidDataException(errorMsg + "(not all required fields are filled)");
        }
        if (!passportData.getPassportNumber().matches("[A-Z0-9\\u0020]{6,12}")) {
            throw new InvalidDataException(errorMsg + "(invalid passportNumber)");
        }
    }
}
