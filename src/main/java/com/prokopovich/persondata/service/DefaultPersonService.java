package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.service.person.PersonConstructor;
import com.prokopovich.persondata.service.person.PersonModifier;
import com.prokopovich.persondata.util.exception.InvalidUrlException;
import com.prokopovich.persondata.util.exception.PersonConstructorException;
import com.prokopovich.persondata.util.exception.PersonServiceException;
import com.prokopovich.persondata.webclient.exception.HttpClientException;
import com.prokopovich.persondata.webclient.exception.HttpResponseException;
import com.prokopovich.persondata.webclient.validator.DefaultHttpResponseValidator;
import com.prokopovich.persondata.util.validator.DefaultEnteredUrlValidator;
import com.prokopovich.persondata.webclient.HttpClient;
import com.prokopovich.persondata.webclient.HttpResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final HttpClient httpClient;
    private final PersonConstructor personConstructor;
    private final PersonModifier personModifier;

    private final DefaultEnteredUrlValidator urlValidator;
    private final DefaultHttpResponseValidator responseValidator;

    @Override
    public String getDataFromUrl(String urlStr) {
        Person modifiedPerson;
        try {
            urlValidator.checkEnteredUrl(urlStr);

            HttpResponse httpResponse = httpClient.getData(new URL(urlStr));
            responseValidator.checkHttpResponse(httpResponse);

            Person person = personConstructor.construct(httpResponse.getBody());
            modifiedPerson = personModifier.modifyToDisplay(person);
        } catch (InvalidUrlException e) {
            throw new PersonServiceException("entered invalid URL", e.getCause());
        } catch (HttpClientException e) {
            throw new PersonServiceException("connection error", e.getCause());
        } catch (HttpResponseException e) {
            System.out.println("--------------------" + e.getCause());
            throw new PersonServiceException("HTTP response error", e.getCause());
        } catch (PersonConstructorException e) {
            throw new PersonServiceException("unable to construct Person", e.getCause());
        } catch (IOException e) {
            throw new PersonServiceException(e.getMessage(), e.getCause());
        }
        return modifiedPerson.toString();
    }
}
