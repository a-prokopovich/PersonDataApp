package com.prokopovich.persondata.service.person;

import com.prokopovich.persondata.model.Person;

public class DefaultPersonModifier implements PersonModifier {

    @Override
    public Person modifyToDisplay(Person person) {
        person.setPassportData(null);
        return person;
    }
}
