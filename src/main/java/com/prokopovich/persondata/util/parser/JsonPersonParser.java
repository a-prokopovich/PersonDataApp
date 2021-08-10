package com.prokopovich.persondata.util.parser;

import com.google.gson.Gson;
import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.exception.ParserException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonPersonParser implements PersonParser {

    @Override
    public Person parse(InputStream personIn) throws ParserException {
        String personText;
        try {
            personText = IOUtils.toString(personIn, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new ParserException(e.getMessage());
        }
        return new Gson().fromJson(personText, Person.class);
    }
}
