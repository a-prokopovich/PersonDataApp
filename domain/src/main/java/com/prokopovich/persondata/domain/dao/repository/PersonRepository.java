package com.prokopovich.persondata.domain.dao.repository;

import com.prokopovich.persondata.domain.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

}