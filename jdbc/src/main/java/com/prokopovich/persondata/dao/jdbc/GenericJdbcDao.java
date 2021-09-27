package com.prokopovich.persondata.dao.jdbc;

import com.prokopovich.persondata.domain.dao.api.GenericDao;
import com.prokopovich.persondata.domain.exception.DaoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class GenericJdbcDao<T> implements GenericDao<T> {

    private final DataSource dataSource;

    @Override
    public void create(T newObject) {

        log.debug("Create method started with new object = {}", newObject);
        String sql = getSqlCreate();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setStatement(newObject, statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Unable to create newObject in database: " + e.getMessage());
        }
    }

    @Override
    public Collection<T> findAll() {

        log.debug("Getting object list from database");
        T object;
        List<T> objectsList = new ArrayList<>();

        String sql = getSqlSelectAll();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                object = getStatement(rs);
                objectsList.add(object);
            }
        } catch (SQLException e) {
            throw new DaoException("Unable get object list from database: " + e.getMessage());
        }
        return objectsList;
    }

    @Override
    public T findById(int id) {

        log.debug("Getting object from database with id = {}", id);
        T object = null;
        String sql = getSqlFindById();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs != null && rs.next()) {
                object = getStatement(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Unable to get object by id from database: " + e.getMessage());
        }
        return object;
    }

    @Override
    public void update(int id, T object) {

        log.debug("Update object in database with id = {} and new data: {}", id, object);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(getSqlUpdate())) {

            setUpdateStatement(id, object, statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Unable to update object in database: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {

        log.debug("Delete object from database with id = {}", id);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(getSqlDelete())) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
