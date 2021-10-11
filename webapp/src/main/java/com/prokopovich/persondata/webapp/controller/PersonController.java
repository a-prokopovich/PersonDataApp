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
    public Object getAllPersons() throws PersonServiceException {

        log.info("Getting list of Persons");
        var personList = personService.getPersonList();

        if (personList.isEmpty()) return new MessageResponse("Persons list is empty");

        return personList;
    }

    @PostMapping("")
    public Person getPersonByUrl(@Valid @RequestBody UrlRequest urlRequest) {

        log.info("Getting Person by url: {}", urlRequest.getUrl());

        return personService.getByUrl(urlRequest.getUrl());
    }

    @GetMapping(value = "/{id}")
    public Person getPersonById(@PathVariable int id) {

        log.info("Getting Person by id = {}", id);

        return personService.getById(id);
    }

    @PutMapping(value = "/{id}")
    public MessageResponse updatePerson(@PathVariable int id,
                                        @Valid @RequestBody Person person) {

        log.info("Updating Person by id = {}", id);
        personService.update(id, person);

        return new MessageResponse("Person updated successfully");
    }

    @DeleteMapping(value = "/{id}")
    public MessageResponse deletePerson(@PathVariable int id) {

        log.info("Deleting Person by id = {}", id);
        personService.delete(id);

        return new MessageResponse("Person deleted successfully");
    }
}