package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.exception.PersonConstructorException;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.validator.PersonValidator;
import com.prokopovich.persondata.parser.api.PersonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultPersonConstructor implements PersonConstructor {

    private final PersonParser parser;
    private final PersonValidator personValidator;

    @Override
    public Person construct(byte[] personIn) {

        try {
            var person = parser.parse(personIn);
            personValidator.validate(person);

            log.debug("New person constructed, {}", person);

            return person;
        } catch (Exception e) {
            throw new PersonConstructorException(e.getMessage(), e);
        }
    }
}