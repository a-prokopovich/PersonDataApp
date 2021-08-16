package com.prokopovich.persondata.service.api;

import com.prokopovich.persondata.model.entity.Person;

public interface PersonModifier {

    Person modifyToDisplay(Person person);
}
