package com.prokopovich.persondata.dao.hibernate;

import com.prokopovich.persondata.domain.dao.api.GenericDao;
import com.prokopovich.persondata.domain.exception.DaoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
public abstract class GenericHibernateDao<T> implements GenericDao<T> {

    private final Class<T> className;

    @PersistenceContext
    private EntityManager entityManager;

    protected abstract String getSqlCreate();

    protected abstract String getSqlUpdate();

    protected abstract String getSqlDelete();

    protected abstract void setQueryParameter(T object, Query query);

    protected abstract void setUpdateQueryParameter(int id, T object, Query query);

    @Override
    public void create(T newObject) {

        log.debug("Create method started with new object = {}", newObject);

        try {
            var query = entityManager.createNativeQuery(getSqlCreate());
            setQueryParameter(newObject, query);
            int isSuccessful = query.executeUpdate();

            if (isSuccessful == 0) throw new OptimisticLockException();

        } catch (Exception e) {
            throw new DaoException("Unable to update object from database: " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<T> findAll() {

        log.debug("Getting object list from database");
        var sql = "select e from " + className.getSimpleName() + " e";

        return entityManager.createQuery(sql, className).getResultList();
    }

    @Override
    public T findById(int id) {

        log.debug("Getting object from database with id = {}", id);

        return entityManager.find(className, id);
    }

    @Override
    public void update(int id, T object) {

        log.debug("Update object in database with id = {} and new data: {}", id, object);

        try {
            var query = entityManager.createQuery(getSqlUpdate());
            setUpdateQueryParameter(id, object, query);
            int isSuccessful = query.executeUpdate();

            if (isSuccessful == 0) throw new OptimisticLockException();

        } catch (Exception e) {
            throw new DaoException("Unable to update object from database: " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void delete(int id) {

        log.debug("Delete object from database with id = {}", id);
        try {
            int isSuccessful = entityManager.createQuery(getSqlDelete())
                .setParameter("id", id)
                .executeUpdate();

            if (isSuccessful == 0) throw new OptimisticLockException();

        } catch (Exception e) {
            throw new DaoException("Unable to delete object from database: " + e.getMessage(), e.getCause());
        }
    }
}
