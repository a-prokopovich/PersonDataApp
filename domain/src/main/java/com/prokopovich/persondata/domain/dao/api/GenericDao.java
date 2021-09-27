package com.prokopovich.persondata.domain.dao.api;

import com.prokopovich.persondata.domain.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @throws DaoException if catching SQL error
 */
public interface GenericDao<T> {

    void create(T newObject);

    Collection<T> findAll();

    T findById(int id);

    void update(int id, T object);

    void delete(int id);

    String getSqlCreate();

    String getSqlSelectAll();

    String getSqlFindById();

    String getSqlUpdate();

    String getSqlDelete();

    T getStatement(ResultSet rs) throws SQLException;

    void setStatement(T object, PreparedStatement statement) throws SQLException;

    void setUpdateStatement(int id, T object, PreparedStatement statement) throws SQLException;
}
