package com.prokopovich.persondata.dao.jdbc;

import com.prokopovich.persondata.domain.dao.api.PassportDataDao;
import com.prokopovich.persondata.domain.model.PassportData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassportDataJdbcDao extends GenericJdbcDao<PassportData> implements PassportDataDao {

    private static final String SQL_CREATE = "INSERT INTO persons_db.passport_data (id, passport_number, " +
        "identification_number) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT id, passport_number, identification_number " +
        "FROM persons_db.passport_data";
    private static final String SQL_SELECT_ONE = "SELECT id, passport_number, identification_number " +
        "FROM persons_db.passport_data WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE persons_db.passport_data SET passport_number = ?, " +
        "identification_number = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM persons_db.passport_data WHERE id = ?";

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
    protected PassportData getStatement(ResultSet rs) throws SQLException {

        var passportData = new PassportData();

        passportData.setId(rs.getInt(1));
        passportData.setPassportNumber(rs.getString(2));
        passportData.setIdentificationNumber(rs.getString(3));

        return passportData;
    }

    @Override
    protected void setStatement(PassportData passportData, PreparedStatement statement) throws SQLException {

        statement.setInt(1, passportData.getId());
        statement.setString(2, passportData.getPassportNumber());
        statement.setString(3, passportData.getIdentificationNumber());
    }

    @Override
    protected void setUpdateStatement(int id, PassportData passportData, PreparedStatement statement) throws SQLException {

        statement.setString(1, passportData.getPassportNumber());
        statement.setString(2, passportData.getIdentificationNumber());
        statement.setInt(3, id);
    }
}
