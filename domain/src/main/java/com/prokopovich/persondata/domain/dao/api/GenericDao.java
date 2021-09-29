package com.prokopovich.persondata.domain.dao.api;

import com.prokopovich.persondata.domain.exception.DaoException;

import java.util.List;

/**
 * @throws DaoException if catching SQL error
 */
public interface GenericDao<T> {

    void create(T newObject);

    List<T> findAll();

    T findById(int id);

    void update(int id, T object);

    void delete(int id);
}
