package com.prokopovich.persondata.domain.service.constructor;

import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.exception.PersonConstructorException;

public interface PersonConstructor {

    /**
     * @throws PersonConstructorException if object Person contains invalid data
     * or parsing error
     */
    Person construct(byte[] personIn);
}
