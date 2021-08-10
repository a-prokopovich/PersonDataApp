package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.SerializationObject;
import com.prokopovich.persondata.util.parser.JsonPersonParser;
import com.prokopovich.persondata.util.parser.PersonParser;
import com.prokopovich.persondata.util.validator.ModelDataValidator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PersonService {

    public Person constructPersonObject(InputStream personIn) throws IOException {
        PersonParser parser = new JsonPersonParser();
        Person person = parser.parse(personIn);
        ModelDataValidator validator = new ModelDataValidator();
        validator.checkPersonData(person);
        return person;
    }

    public File getDataToDisplay(Person person) throws IOException {
        Person clonePerson = person.clone();
        clonePerson.setPassportData(null);
        return SerializationObject.serializeObject(clonePerson);
    }
}
