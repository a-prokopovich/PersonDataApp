package com.prokopovich.persondata.domain.service.constructor;

import com.prokopovich.persondata.domain.exception.PersonConstructorException;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.service.parser.PersonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Slf4j
@RequiredArgsConstructor
public class DefaultPersonConstructor implements PersonConstructor {

    private final PersonParser parser;

    @Autowired
    private Validator validator;

    @Override
    public Person construct(byte[] personIn) {

        try {
            var person = parser.parse(personIn);

            var violations = validator.validate(person);
            if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

            log.debug("New person constructed, {}", person);

            return person;
        } catch (Exception e) {
            throw new PersonConstructorException(e.getMessage(), e);
        }
    }
}