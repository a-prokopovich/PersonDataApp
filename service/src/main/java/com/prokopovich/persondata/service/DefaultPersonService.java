package com.prokopovich.persondata.service;

import com.prokopovich.persondata.domain.model.Person;
import com.prokopovich.persondata.domain.service.PersonConstructor;
import com.prokopovich.persondata.domain.service.PersonModifier;
import com.prokopovich.persondata.service.exception.PersonServiceException;
import com.prokopovich.persondata.service.validator.InUrlValidator;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.service.validator.HttpResponseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final HttpClient httpClient;
    private final PersonConstructor personConstructor;
    private final PersonModifier personModifier;

    private final InUrlValidator urlValidator;
    private final HttpResponseValidator responseValidator;

    @Override
    public Person getByUrl(String url) {

        log.info("Getting person by url {}", url);

        if (!urlValidator.checkEnteredUrl(url)) throw new PersonServiceException("entered invalid URL");

        try {
            var httpResponse = httpClient.getData(url);
            responseValidator.checkHttpResponse(httpResponse);

            var person = personConstructor.construct(httpResponse.getBody());

            return personModifier.modify(person);
        } catch (Exception e) {
            throw new PersonServiceException("unexpected error: " + e.getMessage(), e);
        }
    }
}
