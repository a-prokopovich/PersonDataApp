package com.prokopovich.persondata.webapp.config;

import com.prokopovich.persondata.domain.dao.api.PassportDataDao;
import com.prokopovich.persondata.domain.dao.api.PersonDao;
import com.prokopovich.persondata.dao.jdbc.PassportDataJdbcDao;
import com.prokopovich.persondata.dao.jdbc.PersonJdbcDao;
import com.prokopovich.persondata.domain.service.DefaultPersonService;
import com.prokopovich.persondata.domain.service.PersonService;
import com.prokopovich.persondata.domain.service.constructor.DefaultPersonConstructor;
import com.prokopovich.persondata.domain.service.constructor.PersonConstructor;
import com.prokopovich.persondata.domain.service.modifier.DefaultPersonModifier;
import com.prokopovich.persondata.domain.service.modifier.PersonModifier;
import com.prokopovich.persondata.domain.service.parser.JsonPersonParser;
import com.prokopovich.persondata.domain.service.parser.PersonParser;
import com.prokopovich.persondata.domain.validator.httpresponse.DefaultHttpResponseValidator;
import com.prokopovich.persondata.domain.validator.httpresponse.HttpResponseValidator;
import com.prokopovich.persondata.webapp.filter.CORSFilter;
import com.prokopovich.persondata.webapp.filter.CompressionFilter;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.httpclient.DefaultHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class WebConfig {

    private final Environment environment;

    public WebConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public HttpResponseValidator httpResponseValidator() {
        return new DefaultHttpResponseValidator();
    }

    @Bean
    public PersonParser personParser() {
        return new JsonPersonParser();
    }

    @Bean
    public PersonConstructor personConstructor(PersonParser personParser) {
        return new DefaultPersonConstructor(personParser);
    }

    @Bean
    public PersonModifier personModifier() {
        return new DefaultPersonModifier();
    }

    @Bean
    public HttpClient httpClient() {
        return new DefaultHttpClient();
    }

    @Bean
    public PersonService personService(PersonConstructor personConstructor,
                                       PersonModifier personModifier,
                                       HttpClient httpClient,
                                       HttpResponseValidator httpResponseValidator,
                                       PersonDao personDao) {

        return new DefaultPersonService(personConstructor, personModifier,
            httpClient, httpResponseValidator, personDao);
    }

    //@Bean
    //public CORSFilter corsFilter() {
    //    return new CORSFilter();
    //}
//
    //@Bean
    //public CompressionFilter compressorFilter() {
    //    return new CompressionFilter();
    //}

    @Bean
    public DataSource dataSource() {

        var dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean
    public PassportDataDao passportDataDao(DataSource dataSource) {
        return new PassportDataJdbcDao(dataSource);
    }

    @Bean
    public PersonDao personDao(DataSource dataSource, PassportDataDao passportDataDao) {
        return new PersonJdbcDao(dataSource, passportDataDao);
    }

}
