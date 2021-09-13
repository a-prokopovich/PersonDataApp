package com.prokopovich.persondata.domain.service.parser;

import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.exception.ParserException;

public interface PersonParser {

    /**
     * @throws ParserException if error of convert InputStream to String
     * or unable to parse the string
     */
    Person parse(byte[] personIn);
}
