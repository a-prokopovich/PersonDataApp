package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.entity.Person;
import com.prokopovich.persondata.service.api.PersonConstructor;
import com.prokopovich.persondata.model.exception.InvalidDataException;
import com.prokopovich.persondata.service.exception.PersonConstructorException;
import com.prokopovich.persondata.parser.api.PersonParser;
import com.prokopovich.persondata.parser.exception.ParserException;
import com.prokopovich.persondata.model.validator.ModelDataValidator;
import lombok.AllArgsConstructor;

import java.io.InputStream;

@AllArgsConstructor
public class DefaultPersonConstructor implements PersonConstructor {

    private final PersonParser parser;
    private final ModelDataValidator validator;

    @Override
    public Person construct(InputStream personIn) {
        Person person;
        try {
            person = parser.parse(personIn);
            validator.checkPersonData(person);
        } catch (ParserException e) {
            throw new PersonConstructorException("parsing error", e);
        } catch (InvalidDataException e) {
            throw new PersonConstructorException("invalid data", e);
        }
        return person;
    }
}
