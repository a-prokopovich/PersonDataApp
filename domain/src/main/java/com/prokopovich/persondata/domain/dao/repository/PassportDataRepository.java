package com.prokopovich.persondata.domain.dao.repository;

import com.prokopovich.persondata.domain.model.PassportData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportDataRepository extends CrudRepository<PassportData, Integer> {
}
