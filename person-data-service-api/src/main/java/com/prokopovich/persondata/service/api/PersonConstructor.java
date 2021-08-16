package com.prokopovich.persondata.service.api;

import com.prokopovich.persondata.model.entity.Person;
import com.prokopovich.persondata.service.exception.PersonConstructorException;

import java.io.InputStream;

public interface PersonConstructor {

    /**
     * @throws PersonConstructorException if object Person contains invalid data
     * or parsing error
     */
    Person construct(InputStream personIn);
}
