package com.prokopovich.persondata.domain.service.parser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.exception.ParserException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonPersonParser implements PersonParser {

    @Override
    public Person parse(byte[] personIn) {

        log.debug("Parsing byte array to person: {}", new String(personIn));

        try {
            return new Gson().fromJson(
                new String(personIn),
                Person.class);

        } catch (JsonSyntaxException e) {
            throw new ParserException(e.getMessage());
        }
    }
}
