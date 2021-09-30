package com.prokopovich.persondata.domain.service;

import com.prokopovich.persondata.domain.dao.repository.PassportDataRepository;
import com.prokopovich.persondata.domain.dao.repository.PersonRepository;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.service.constructor.PersonConstructor;
import com.prokopovich.persondata.domain.service.modifier.PersonModifier;
import com.prokopovich.persondata.domain.exception.PersonServiceException;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.domain.validator.httpresponse.HttpResponseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final PersonConstructor personConstructor;
    private final PersonModifier personModifier;

    private final HttpClient httpClient;
    private final HttpResponseValidator responseValidator;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PassportDataRepository passportDataRepository;

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
        personRepository.save(person);
        passportDataRepository.save(person.getPassportData());
    }

    @Override
    public Collection<Person> getPersonList() {

        var personList = personRepository.findAll();
        if (!personList.iterator().hasNext()) throw new PersonServiceException("Persons list is empty", 404);

        return (Collection<Person>) personList;
    }

    @Override
    public Person getById(int id) {

        var person = personRepository.findById(id);
        if (person.isEmpty()) throw new PersonServiceException("Unable to get Person by Id: Person not found", 404);

        return person.get();
    }

    @Override
    public void update(int id, Person modifiedPerson) {

        log.debug("Updating person by id {} with new information: {}", id, modifiedPerson);
        personRepository.save(modifiedPerson);
    }

    @Override
    public void delete(int id) {
        passportDataRepository.deleteById(id);
        personRepository.deleteById(id);
    }
}
