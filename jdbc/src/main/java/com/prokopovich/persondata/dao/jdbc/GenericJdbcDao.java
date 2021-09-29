package com.prokopovich.persondata.dao.jdbc;

import com.prokopovich.persondata.domain.dao.api.GenericDao;
import com.prokopovich.persondata.domain.exception.DaoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class GenericJdbcDao<T> implements GenericDao<T> {

    @Autowired
    private DataSource dataSource;

    protected abstract String getSqlCreate();

    protected abstract String getSqlSelectAll();

    protected abstract String getSqlFindById();

    protected abstract String getSqlUpdate();

    protected abstract String getSqlDelete();

    protected abstract T getStatement(ResultSet rs) throws SQLException;

    protected abstract void setStatement(T object, PreparedStatement statement) throws SQLException;

    protected abstract void setUpdateStatement(int id, T object, PreparedStatement statement) throws SQLException;

    @Override
    public void create(T newObject) {

        log.debug("Create method started with new object = {}", newObject);

        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(getSqlCreate())) {

            setStatement(newObject, statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Unable to create newObject in database: " + e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {

        log.debug("Getting object list from database");

        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(getSqlSelectAll())) {

            var rs = statement.executeQuery();

            List<T> objectsList = new ArrayList<>();
            while (rs.next()) {
                T object = getStatement(rs);
                objectsList.add(object);
            }

            return objectsList;
        } catch (SQLException e) {
            throw new DaoException("Unable get object list from database: " + e.getMessage());
        }
    }

    @Override
    public T findById(int id) {

        log.debug("Getting object from database with id = {}", id);

        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(getSqlFindById())) {

            statement.setInt(1, id);
            var rs = statement.executeQuery();
            if (rs != null && rs.next()) {

                return getStatement(rs);

            } else return null;

        } catch (SQLException e) {
            throw new DaoException("Unable to get object by id from database: " + e.getMessage());
        }
    }

    @Override
    public void update(int id, T object) {

        log.debug("Update object in database with id = {} and new data: {}", id, object);

        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(getSqlUpdate())) {

            setUpdateStatement(id, object, statement);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Unable to update object in database: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {

        log.debug("Delete object from database with id = {}", id);

        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(getSqlDelete())) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Unable to delete object from database: " + e.getMessage());
        }
    }
}
