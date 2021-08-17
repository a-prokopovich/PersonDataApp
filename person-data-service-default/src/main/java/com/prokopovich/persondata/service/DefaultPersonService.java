package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.entity.Person;
import com.prokopovich.persondata.service.api.PersonConstructor;
import com.prokopovich.persondata.service.api.PersonModifier;
import com.prokopovich.persondata.service.api.PersonService;
import com.prokopovich.persondata.service.exception.PersonConstructorException;
import com.prokopovich.persondata.service.exception.PersonServiceException;
import com.prokopovich.persondata.service.validator.EnteredUrlValidator;
import com.prokopovich.persondata.webclient.exception.HttpClientException;
import com.prokopovich.persondata.webclient.exception.HttpResponseException;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.response.HttpResponse;
import com.prokopovich.persondata.webclient.validator.HttpResponseValidator;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private static final Logger LOGGER = LogManager.getLogger(DefaultPersonService.class);

    private final HttpClient httpClient;
    private final PersonConstructor personConstructor;
    private final PersonModifier personModifier;

    private final EnteredUrlValidator urlValidator;
    private final HttpResponseValidator responseValidator;

    @Override
    public String getDataFromUrl(String url) {
        LOGGER.debug("getDataFromUrl method is executed with url = " + url);

        Person modifiedPerson;
        try {
            if (!urlValidator.checkEnteredUrl(url)) {
                throw new PersonServiceException("entered invalid URL");
            }

            HttpResponse httpResponse = httpClient.getData(url);
            LOGGER.debug("received a response from webclient - " + httpResponse);
            responseValidator.checkHttpResponse(httpResponse);

            Person person = personConstructor.construct(httpResponse.getBody());
            modifiedPerson = personModifier.modifyToDisplay(person);

        } catch (HttpClientException e) {
            throw new PersonServiceException("connection error", e);
        } catch (HttpResponseException e) {
            throw new PersonServiceException("HTTP response error", e);
        } catch (PersonConstructorException e) {
            throw new PersonServiceException("unable to construct Person", e);
        }
        return modifiedPerson.toString();
    }
}
