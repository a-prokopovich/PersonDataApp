package com.prokopovich.persondata.webapp.config;

import com.prokopovich.persondata.domain.dao.api.PersonDao;
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

@Configuration
public class WebConfig {

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
}
