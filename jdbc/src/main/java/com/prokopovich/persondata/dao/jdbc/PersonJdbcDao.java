package com.prokopovich.persondata.dao.jdbc;

import com.prokopovich.persondata.domain.dao.api.PassportDataDao;
import com.prokopovich.persondata.domain.dao.api.PersonDao;
import com.prokopovich.persondata.domain.model.Person;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonJdbcDao extends GenericJdbcDao<Person> implements PersonDao {

    private static final String SQL_CREATE = "INSERT INTO persons_db.persons (id, full_name, phone, email) " +
        "VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT id, full_name, phone, email FROM persons_db.persons";
    private static final String SQL_SELECT_ONE = "SELECT id, full_name, phone, email FROM persons_db.persons WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE persons_db.persons SET full_name = ?, phone = ?, email = ? " +
        "WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM persons_db.persons WHERE id = ?";

    private final PassportDataDao passportDataDao;

    public PersonJdbcDao(DataSource dataSource, PassportDataDao passportDataDao) {
        super(dataSource);
        this.passportDataDao = passportDataDao;
    }

    @Override
    public void create(Person newObject) {

        super.create(newObject);
        passportDataDao.create(newObject.getPassportData());
    }

    @Override
    public void update(int id, Person person) {
        super.update(id, person);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public String getSqlCreate() {
        return SQL_CREATE;
    }

    @Override
    public String getSqlSelectAll() {
        return SQL_SELECT_ALL;
    }

    @Override
    public String getSqlFindById() {
        return SQL_SELECT_ONE;
    }

    @Override
    public String getSqlUpdate() {
        return SQL_UPDATE;
    }

    @Override
    public String getSqlDelete() {
        return SQL_DELETE;
    }

    @Override
    public Person getStatement(ResultSet rs) throws SQLException {

        Person person = new Person();

        person.setId(rs.getInt(1));
        person.setFullName(rs.getString(2));
        person.setPhone(rs.getString(3));
        person.setEmail(rs.getString(4));

        person.setPassportData(passportDataDao.findByPersonId(person.getId()));

        return person;
    }

    @Override
    public void setStatement(Person person, PreparedStatement statement) throws SQLException {

        statement.setInt(1, person.getId());
        statement.setString(2, person.getFullName());
        statement.setString(3, person.getPhone());
        statement.setString(4, person.getEmail());
    }

    @Override
    public void setUpdateStatement(int id, Person person, PreparedStatement statement) throws SQLException {

        setStatement(person, statement);
        statement.setInt(4, id);
    }
}
