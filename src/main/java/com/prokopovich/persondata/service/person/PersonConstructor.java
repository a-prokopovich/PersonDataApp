package com.prokopovich.persondata.service.person;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.exception.PersonConstructorException;

import java.io.InputStream;

public interface PersonConstructor {

    /**
     * @throws PersonConstructorException if object Person contains invalid data
     * or parsing error
     */
    Person construct(InputStream personIn);
}
