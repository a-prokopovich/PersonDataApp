package com.prokopovich.persondata.service.person;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.exception.InvalidDataException;
import com.prokopovich.persondata.util.exception.PersonConstructorException;
import com.prokopovich.persondata.util.parser.PersonParser;
import com.prokopovich.persondata.util.parser.exception.ParserException;
import com.prokopovich.persondata.util.validator.ModelDataValidator;
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
