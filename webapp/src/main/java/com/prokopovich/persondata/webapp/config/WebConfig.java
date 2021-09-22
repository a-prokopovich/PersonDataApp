package com.prokopovich.persondata.webapp.config;

import com.prokopovich.persondata.domain.cache.Cache;
import com.prokopovich.persondata.domain.cache.PersonListCache;
import com.prokopovich.persondata.domain.model.Person;
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
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.httpclient.DefaultHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    private static final int CACHE_SIZE = 3;

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
    public Cache<Integer, Person> cache() {
        return new PersonListCache(CACHE_SIZE);
    }

    @Bean
    public PersonService personService(PersonConstructor personConstructor,
                                       PersonModifier personModifier,
                                       HttpClient httpClient,
                                       HttpResponseValidator httpResponseValidator,
                                       Cache<Integer, Person> cache) {

        return new DefaultPersonService(personConstructor, personModifier,
            httpClient, httpResponseValidator, cache);
    }
}
