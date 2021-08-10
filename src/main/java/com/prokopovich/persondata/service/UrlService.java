package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.validator.HttpResponseValidator;
import com.prokopovich.persondata.util.validator.UriValidator;
import com.prokopovich.persondata.util.webclient.CustomClient;
import com.prokopovich.persondata.util.webclient.CustomResponse;

import java.io.IOException;
import java.net.URL;

public class UrlService {

    private final CustomClient httpClient;
    private final PersonService personService;

    public UrlService(CustomClient httpClient, PersonService personService) {
        this.httpClient = httpClient;
        this.personService = personService;
    }

    public String getDataFromUrl(String urlStr) throws IOException, ClassNotFoundException {
        UriValidator uriValidator = new UriValidator();
        uriValidator.checkEnteredUrl(urlStr);

        CustomResponse httpResponse = httpClient.getData(new URL(urlStr));
        HttpResponseValidator responseValidator = new HttpResponseValidator();
        responseValidator.checkHttpResponse(httpResponse);

        Person person = personService.constructPersonObject(httpResponse.getBody());
        Person modifiedPerson = personService.modifyPersonToDisplay(person);

        return modifiedPerson.toString();
    }
}
