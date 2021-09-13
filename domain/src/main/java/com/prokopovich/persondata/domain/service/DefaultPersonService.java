package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.cache.Cache;
import com.prokopovich.persondata.domain.exception.InvalidDataException;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.service.constructor.PersonConstructor;
import com.prokopovich.persondata.domain.service.modifier.PersonModifier;
import com.prokopovich.persondata.domain.validator.person.PersonValidator;
import com.prokopovich.persondata.domain.exception.PersonServiceException;
import com.prokopovich.persondata.domain.validator.inurl.InUrlValidator;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.domain.validator.httpresponse.HttpResponseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final HttpClient httpClient;
    private final PersonConstructor personConstructor;
    private final PersonModifier personModifier;

    private final PersonValidator personValidator;
    private final InUrlValidator urlValidator;
    private final HttpResponseValidator responseValidator;

    private final Cache personListCache;

    @Override
    public Person getByUrl(String url) {

        log.info("Getting person by url {}", url);

        if (!urlValidator.checkEnteredUrl(url)) throw new PersonServiceException("entered invalid URL", 400);

        try {
            var httpResponse = httpClient.getData(url);
            responseValidator.checkHttpResponse(httpResponse);

            var person = personConstructor.construct(httpResponse.getBody());

            return personModifier.modify(person);
        } catch (Exception e) {
            throw new PersonServiceException("Unable to get Person from URL: " + e.getMessage(), e, 500);
        }
    }

    @Override
    public Person getById(int id) {

        log.info("Getting person by id {}", id);

        var person = getById(id);
        if (person == null) throw new PersonServiceException("Unable to get Person by Id: Person not found", 404);

        return person;
    }

    @Override
    public void putToListCache(Person person) {
        personListCache.put(person.getId(), person);
    }

    @Override
    public Collection<Person> getListByCache() {
        Collection<Person> personList = personListCache.values();
        if (personList.isEmpty()) throw new PersonServiceException("Persons list is empty", 404);
        return personList;
    }

    @Override
    public void update(int id, Person modifiedPerson) {

        log.info("Update person by id {} with new information: {}", id, modifiedPerson);

        try {
            checkContainsPerson(id);
            personValidator.validate(modifiedPerson);

            personListCache.put(id, modifiedPerson);
        } catch (InvalidDataException e) {
            throw new PersonServiceException("Unable updated Person: " + e.getMessage(), 404);
        }
    }

    @Override
    public void delete(int id) {

        log.info("Delete person by id {}", id);

        checkContainsPerson(id);
        personListCache.remove(id);
    }

    private void checkContainsPerson(int id) throws PersonServiceException {

        if (!personListCache.containsKey(id))
            throw new PersonServiceException("Unable delete Person: Person by Id not found", 404);
    }
}
