package com.prokopovich.persondata.domain.validator.person;

import com.prokopovich.persondata.domain.model.PassportData;
import com.prokopovich.persondata.domain.model.Person;

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
