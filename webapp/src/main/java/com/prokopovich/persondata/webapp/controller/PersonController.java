package com.prokopovich.persondata.webapp.controller;

import com.prokopovich.persondata.domain.exception.PersonServiceException;
import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.service.PersonService;

import com.prokopovich.persondata.webapp.request.UrlRequest;
import com.prokopovich.persondata.webapp.response.MessageResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/persons")
@Slf4j
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("")
    public Collection<Person> getAllPersons() throws PersonServiceException {

        return personService.getListByCache();
    }

    @PostMapping("")
    public Person getPersonByUrl(@Valid @RequestBody UrlRequest urlRequest) {

        var person = personService.getByUrl(urlRequest.getUrl());
        personService.putToListCache(person);

        return person;
    }

    @GetMapping(value = "/{id}")
    public Person getPersonById(@PathVariable int id) {

        return personService.getById(id);
    }

    @PutMapping(value = "/{id}")
    public MessageResponse updatePerson(@PathVariable int id,
                                        @Valid @RequestBody Person person) {

        personService.update(id, person);

        return new MessageResponse("Person updated successfully");
    }

    @DeleteMapping(value = "/{id}")
    public MessageResponse deletePerson(@PathVariable int id) {

        personService.delete(id);

        return new MessageResponse("Person deleted successfully");
    }
}