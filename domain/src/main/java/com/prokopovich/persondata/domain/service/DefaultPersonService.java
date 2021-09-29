package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.dao.api.PersonDao;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.service.constructor.PersonConstructor;
import com.prokopovich.persondata.domain.service.modifier.PersonModifier;
import com.prokopovich.persondata.domain.exception.PersonServiceException;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.domain.validator.httpresponse.HttpResponseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final PersonConstructor personConstructor;
    private final PersonModifier personModifier;

    private final HttpClient httpClient;
    private final HttpResponseValidator responseValidator;

    private final PersonDao personDao;

    @Override
    public Person getByUrl(String url) {

        try {
            var httpResponse = httpClient.getData(url);
            responseValidator.checkHttpResponse(httpResponse);

            var person = personConstructor.construct(httpResponse.getBody());
            save(person);

            return personModifier.modify(person);
        } catch (Exception e) {
            throw new PersonServiceException("Unable to get Person from URL: " + e.getMessage(), e, 500);
        }
    }

    @Override
    public void save(Person person) {

        log.info("Saving new Person");
        personDao.create(person);
    }

    @Override
    public Collection<Person> getPersonList() {

        Collection<Person> personList = personDao.findAll();
        if (personList.isEmpty()) throw new PersonServiceException("Persons list is empty", 404);

        return personList;
    }

    @Override
    public Person getById(int id) {

        var person = personDao.findById(id);
        if (person == null) throw new PersonServiceException("Unable to get Person by Id: Person not found", 404);

        return person;
    }

    @Override
    public void update(int id, Person modifiedPerson) {

        log.debug("Updating person by id {} with new information: {}", id, modifiedPerson);
        personDao.update(id, modifiedPerson);
    }

    @Override
    public void delete(int id) {
        personDao.delete(id);
    }
}
