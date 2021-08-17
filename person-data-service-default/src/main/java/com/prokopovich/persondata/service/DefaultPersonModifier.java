package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.entity.Person;
import com.prokopovich.persondata.service.api.PersonModifier;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DefaultPersonModifier implements PersonModifier {

    private static final Logger LOGGER = LogManager.getLogger(DefaultPersonModifier.class);

    @Override
    public Person modifyToDisplay(Person person) {
        LOGGER.trace("modify Person method is executed: set PassportData of null");
        person.setPassportData(null);
        LOGGER.debug("person modified - " + person + "PassportData: " + person.getPassportData());
        return person;
    }
}
