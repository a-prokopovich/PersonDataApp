package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.entity.Person;
import com.prokopovich.persondata.service.api.PersonModifier;

public class DefaultPersonModifier implements PersonModifier {

    @Override
    public Person modifyToDisplay(Person person) {
        person.setPassportData(null);
        return person;
    }
}
