package com.prokopovich.persondata.dao.jdbc;

import com.prokopovich.persondata.domain.dao.api.PassportDataDao;
import com.prokopovich.persondata.domain.exception.DaoException;
import com.prokopovich.persondata.domain.model.PassportData;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class PassportDataJdbcDao extends GenericJdbcDao<PassportData> implements PassportDataDao {

    private static final String SQL_CREATE = "INSERT INTO persons_db.passport_data (person_id, passport_number, " +
        "identification_number) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT id, person_id, passport_number, identification_number " +
        "FROM persons_db.passport_data";
    private static final String SQL_SELECT_ONE = "SELECT id, person_id, passport_number, identification_number " +
        "FROM persons_db.passport_data WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE persons_db.passport_data SET person_id = ?, " +
        "passport_number = ?, identification_number = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM persons_db.passport_data WHERE id = ?";
    private static final String SQL_BY_PERSON_ID = "SELECT id, person_id, passport_number, identification_number " +
        "FROM persons_db.passport_data WHERE person_id = ?";

    private final DataSource dataSource;

    public PassportDataJdbcDao(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
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
    public PassportData getStatement(ResultSet rs) throws SQLException {

        PassportData passportData = new PassportData();

        passportData.setId(rs.getInt(1));
        passportData.setPersonId(rs.getInt(2));
        passportData.setPassportNumber(rs.getString(3));
        passportData.setIdentificationNumber(rs.getString(4));

        return passportData;
    }

    @Override
    public void setStatement(PassportData passportData, PreparedStatement statement) throws SQLException {

        statement.setInt(1, passportData.getPersonId());
        statement.setString(2, passportData.getPassportNumber());
        statement.setString(3, passportData.getIdentificationNumber());
    }

    @Override
    public void setUpdateStatement(int id, PassportData passportData, PreparedStatement statement) throws SQLException {

        setStatement(passportData, statement);
        statement.setInt(4, id);
    }

    @Override
    public PassportData findByPersonId(int id) {

        log.debug("Getting passport data from database with person id = {}", id);
        PassportData passportData = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_BY_PERSON_ID)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs != null && rs.next()) {
                passportData = getStatement(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Unable to get passport data by person id from database: " + e.getMessage());
        }

        return passportData;
    }
}
