package com.prokopovich.persondata.util.parser;

import com.google.gson.Gson;
import com.prokopovich.persondata.model.Person;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonPersonParser implements PersonParser {

    @Override
    public Person parse(InputStream personIn) throws IOException {
        String personText = IOUtils.toString(personIn, StandardCharsets.UTF_8.name());
        return new Gson().fromJson(personText, Person.class);
    }
}
