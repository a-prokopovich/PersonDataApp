package com.prokopovich.persondata.model.validator;

import com.prokopovich.persondata.model.entity.PassportData;
import com.prokopovich.persondata.model.entity.Person;

public class RequiredFieldValidator {

    public boolean containsUnfilledFields(Person person) {
        return person.getId() == 0
                || person.getFullName() == null
                || person.getFullName().equals("");
    }

    public boolean containsUnfilledFields(PassportData passportData) {
        return passportData.getPersonId() == 0
                || passportData.getPassportNumber() == null
                || passportData.getPassportNumber().equals("")
                || passportData.getIdentificationNumber() == null
                || passportData.getIdentificationNumber().equals("");
    }
}
