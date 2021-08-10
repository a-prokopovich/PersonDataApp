package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.SerializationObject;
import com.prokopovich.persondata.util.validator.HttpResponseValidator;
import com.prokopovich.persondata.util.validator.UriValidator;
import com.prokopovich.persondata.util.webclient.CustomClient;
import com.prokopovich.persondata.util.webclient.CustomHttpClient;
import com.prokopovich.persondata.util.webclient.CustomResponse;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UrlService {

    private final PersonService personService = new PersonService();

    public String getDataFromUrl(String urlStr) throws IOException, ClassNotFoundException {
        UriValidator uriValidator = new UriValidator();
        uriValidator.checkEnteredUrl(urlStr);

        CustomClient httpClient = new CustomHttpClient();
        CustomResponse httpResponse = httpClient.getData(new URL(urlStr));
        HttpResponseValidator responseValidator = new HttpResponseValidator();
        responseValidator.checkHttpResponse(httpResponse);

        Person person = personService.constructPersonObject(httpResponse.getBody());

        File file = personService.getDataToDisplay(person);
        Person modifiedPerson = SerializationObject.deserializeObject(file);

        return modifiedPerson.toString();
    }
}
