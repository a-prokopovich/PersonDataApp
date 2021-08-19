package com.prokopovich.persondata.parser.api;

import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.parser.exception.ParserException;

import java.io.InputStream;

public interface PersonParser {

    /**
     * @throws ParserException if error of convert InputStream to String
     * or unable to parse the string
     */
    Person parse(InputStream personIn);
}
