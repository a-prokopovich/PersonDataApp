package com.prokopovich.persondata.service;

import com.prokopovich.persondata.domain.model.Person;

public interface PersonService {

    Person getByUrl(String urlStr);
}
