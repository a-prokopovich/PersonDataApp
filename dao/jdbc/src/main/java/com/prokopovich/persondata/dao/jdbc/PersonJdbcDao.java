package com.prokopovich.persondata.dao.jdbc;

import com.prokopovich.persondata.domain.dao.api.PassportDataDao;
import com.prokopovich.persondata.domain.dao.api.PersonDao;
import com.prokopovich.persondata.domain.model.Person;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class PersonJdbcDao extends GenericJdbcDao<Person> implements PersonDao {

    private static final String SQL_CREATE = "INSERT INTO persons_db.persons (id, full_name, phone, email) " +
        "VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT id, full_name, phone, email FROM persons_db.persons";
    private static final String SQL_SELECT_ONE = "SELECT id, full_name, phone, email FROM persons_db.persons WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE persons_db.persons SET full_name = ?, phone = ?, email = ? " +
        "WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM persons_db.persons WHERE id = ?";

    private final PassportDataDao passportDataDao;

    @Override
    public void create(Person newObject) {

        super.create(newObject);
        passportDataDao.create(newObject.getPassportData());
    }

    @Override
    protected String getSqlCreate() {
        return SQL_CREATE;
    }

    @Override
    protected String getSqlSelectAll() {
        return SQL_SELECT_ALL;
    }

    @Override
    protected String getSqlFindById() {
        return SQL_SELECT_ONE;
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
    protected Person getStatement(ResultSet rs) throws SQLException {

        var person = new Person();

        person.setId(rs.getInt(1));
        person.setFullName(rs.getString(2));
        person.setPhone(rs.getString(3));
        person.setEmail(rs.getString(4));

        person.setPassportData(passportDataDao.findById(person.getId()));

        return person;
    }

    @Override
    protected void setStatement(Person person, PreparedStatement statement) throws SQLException {

        statement.setInt(1, person.getId());
        statement.setString(2, person.getFullName());
        statement.setString(3, person.getPhone());
        statement.setString(4, person.getEmail());
    }

    @Override
    protected void setUpdateStatement(int id, Person person, PreparedStatement statement) throws SQLException {

        statement.setString(1, person.getFullName());
        statement.setString(2, person.getPhone());
        statement.setString(3, person.getEmail());
        statement.setInt(4, id);
    }
}
