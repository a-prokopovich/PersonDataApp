package com.prokopovich.persondata.dao.hibernate;

import com.prokopovich.persondata.domain.dao.api.PassportDataDao;
import com.prokopovich.persondata.domain.model.PassportData;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Query;

@Slf4j
public class PassportDataHibernateDao extends GenericHibernateDao<PassportData> implements PassportDataDao {

    private static final String SQL_CREATE = "INSERT INTO persons_db.passport_data (id, passport_number, " +
        "identification_number) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "update PassportData e SET e.passportNumber=:passportNumber, " +
        "e.identificationNumber=:identificationNumber where e.id=:id";
    private static final String SQL_DELETE = "delete from PassportData e where e.id=:id";

    public PassportDataHibernateDao() {
        super(PassportData.class);
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
    protected void setQueryParameter(PassportData passportData, Query query) {

        query.setParameter(1, passportData.getId())
            .setParameter(2, passportData.getPassportNumber())
            .setParameter(3, passportData.getIdentificationNumber());
    }

    @Override
    protected void setUpdateQueryParameter(int id, PassportData passportData, Query query) {

        query.setParameter("passportNumber", passportData.getPassportNumber())
            .setParameter("identificationNumber", passportData.getIdentificationNumber())
            .setParameter("id", passportData.getId());
    }
}
