package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.exception.InvalidDataException;
import com.prokopovich.persondata.domain.exception.PersonConstructorException;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.validator.PersonValidator;
import com.prokopovich.persondata.parser.api.PersonParser;
import com.prokopovich.persondata.parser.exception.ParserException;

import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;

@AllArgsConstructor
public class DefaultPersonConstructor implements PersonConstructor {

    private static final Logger LOGGER = LogManager.getLogger(DefaultPersonConstructor.class);

    private final PersonParser parser;
    private final PersonValidator personValidator;

    @Override
    public Person construct(InputStream personIn) {
        LOGGER.trace("construct Person method is executed");

        Person person;
        try {
            person = parser.parse(personIn);
            personValidator.validate(person);
            LOGGER.debug("new person constructed - " + person);
        } catch (ParserException e) {
            throw new PersonConstructorException("parsing error", e);
        } catch (InvalidDataException e) {
            throw new PersonConstructorException("invalid data", e);
        }
        return person;
    }
}