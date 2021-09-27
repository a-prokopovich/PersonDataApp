package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.model.Person;

import java.util.Collection;

public interface PersonService {

    Person getByUrl(String urlStr);

    void save(Person person);

    Collection<Person> getPersonList();

    Person getById(int id);

    void update(int id, Person modifiedPerson);

    void delete(int id);
}
