package com.prokopovich.persondata.parser.jsonparser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.prokopovich.persondata.parser.api.PersonParser;
import com.prokopovich.persondata.model.entity.Person;
import com.prokopovich.persondata.parser.exception.ParserException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonPersonParser implements PersonParser {

    @Override
    public Person parse(InputStream personIn) {
        String personText;
        Person person;
        try {
            personText = IOUtils.toString(personIn, StandardCharsets.UTF_8.name());
            person = new Gson().fromJson(personText, Person.class);
        } catch (JsonSyntaxException e) {
            throw new ParserException(e.getMessage());
        } catch (IOException e) {
            throw new ParserException("error of convert InputStream to String");
        }
        return person;
    }
}
