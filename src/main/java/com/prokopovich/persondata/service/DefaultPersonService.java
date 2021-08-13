package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.service.person.PersonConstructor;
import com.prokopovich.persondata.service.person.PersonModifier;
import com.prokopovich.persondata.util.exception.PersonConstructorException;
import com.prokopovich.persondata.util.exception.PersonServiceException;
import com.prokopovich.persondata.webclient.exception.HttpClientException;
import com.prokopovich.persondata.webclient.exception.HttpResponseException;
import com.prokopovich.persondata.webclient.validator.DefaultHttpResponseValidator;
import com.prokopovich.persondata.util.validator.DefaultEnteredUrlValidator;
import com.prokopovich.persondata.webclient.HttpClient;
import com.prokopovich.persondata.webclient.HttpResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final HttpClient httpClient;
    private final PersonConstructor personConstructor;
    private final PersonModifier personModifier;

    private final DefaultEnteredUrlValidator urlValidator;
    private final DefaultHttpResponseValidator responseValidator;

    @Override
    public String getDataFromUrl(String url) {
        Person modifiedPerson;
        try {
            if (!urlValidator.checkEnteredUrl(url)) {
                throw new PersonServiceException("entered invalid URL");
            }

            HttpResponse httpResponse = httpClient.getData(url);
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
