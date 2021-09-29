package com.prokopovich.persondata.dao.hibernate;

import com.prokopovich.persondata.domain.dao.api.PassportDataDao;
import com.prokopovich.persondata.domain.dao.api.PersonDao;
import com.prokopovich.persondata.domain.model.Person;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Query;
import java.util.List;

@Slf4j
public class PersonHibernateDao extends GenericHibernateDao<Person> implements PersonDao {

    private static final String SQL_CREATE = "INSERT INTO persons_db.persons (id, full_name, phone, email) "+
        "VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "update Person e SET e.fullName=:fullName, e.phone=:phone, " +
        "e.email=:email where e.id=:id";
    private static final String SQL_DELETE = "delete from Person e where e.id=:id";

    private final PassportDataDao passportDataDao;

    public PersonHibernateDao(PassportDataDao passportDataDao) {

        super(Person.class);
        this.passportDataDao = passportDataDao;
    }

    @Override
    protected String getSqlCreate() {
        return SQL_CREATE;
    }

    @Override
    protected String getSqlUpdate() {
        return SQL_UPDATE;
    }

    @Override
    protected String getSqlDelete() {
        return SQL_DELETE;
    }

    @Override
    public void create(Person newPerson) {

        super.create(newPerson);
        passportDataDao.create(newPerson.getPassportData());
    }

    @Override
    public List<Person> findAll() {

        var personList = super.findAll();
        for (Person person : personList) {
            person.setPassportData(passportDataDao.findById(person.getId()));
        }

        return personList;
    }

    @Override
    public void update(int id, Person person) {

        super.update(id, person);
        passportDataDao.update(id, person.getPassportData());
    }

    @Override
    protected void setQueryParameter(Person person, Query query) {

        query.setParameter(1, person.getId())
            .setParameter(2, person.getFullName())
            .setParameter(3, person.getPhone())
            .setParameter(4, person.getEmail());
    }

    @Override
    protected void setUpdateQueryParameter(int id, Person person, Query query) {

        query.setParameter("fullName", person.getFullName())
            .setParameter("phone", person.getPhone())
            .setParameter("email", person.getEmail())
            .setParameter("id", id);
    }
}
