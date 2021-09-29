package com.prokopovich.persondata.webapp.config;

import com.prokopovich.persondata.dao.hibernate.PassportDataHibernateDao;
import com.prokopovich.persondata.dao.hibernate.PersonHibernateDao;
import com.prokopovich.persondata.domain.dao.api.PassportDataDao;
import com.prokopovich.persondata.domain.dao.api.PersonDao;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.prokopovich.persondata.domain.model")
public class DBConfig {

    @Bean
    public PassportDataDao passportDataDao() {
        return new PassportDataHibernateDao();
    }

    @Bean
    public PersonDao personDao(PassportDataDao passportDataDao) {
        return new PersonHibernateDao(passportDataDao);
    }
}
