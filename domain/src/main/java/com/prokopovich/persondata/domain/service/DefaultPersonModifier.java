package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.model.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultPersonModifier implements PersonModifier {

    @Override
    public Person modify(Person person) {

        log.info("Hiding passport data for person '{}'", person.getFullName());

        person.setPassportData(null);

        log.debug("Person modified: {}", person);

        return person;
    }
}
