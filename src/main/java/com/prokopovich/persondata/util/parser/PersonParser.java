package com.prokopovich.persondata.util.parser;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.parser.exception.ParserException;

import java.io.InputStream;

public interface PersonParser {

    /**
     * @throws ParserException if error of convert InputStream to String
     * or unable to parse the string
     */
    Person parse(InputStream personIn);
}
