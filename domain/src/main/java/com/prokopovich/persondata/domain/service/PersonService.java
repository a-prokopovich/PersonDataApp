package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.model.Person;

import java.util.Collection;

public interface PersonService {

    Person getByUrl(String urlStr);

    Person getById(int id);

    void putToListCache(Person person);

    Collection<Person> getListByCache();

    void update(int id, Person modifiedPerson);

    void delete(int id);
}
