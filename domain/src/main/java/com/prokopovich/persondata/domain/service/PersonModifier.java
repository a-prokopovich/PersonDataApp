package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.model.Person;

public interface PersonModifier {

    Person modifyToDisplay(Person person);
}
