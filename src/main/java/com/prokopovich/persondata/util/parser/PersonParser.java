package com.prokopovich.persondata.util.parser;

import com.prokopovich.persondata.model.Person;

import java.io.IOException;
import java.io.InputStream;

public interface PersonParser {

    Person parse(InputStream personIn) throws IOException;
}
