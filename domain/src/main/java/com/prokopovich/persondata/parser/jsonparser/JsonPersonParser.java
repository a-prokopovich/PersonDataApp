package com.prokopovich.persondata.parser.jsonparser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.prokopovich.persondata.parser.api.PersonParser;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.parser.exception.ParserException;

import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

@Slf4j
public class JsonPersonParser implements PersonParser {

    @Override
    public Person parse(byte[] personIn) {

        log.info("Parsing byte array ro person: {}", Arrays.toString(personIn));

        try {
            return new Gson().fromJson(
                new String(personIn),
                Person.class);
        } catch (JsonSyntaxException e) {
            throw new ParserException(e.getMessage());
        }
    }
}
