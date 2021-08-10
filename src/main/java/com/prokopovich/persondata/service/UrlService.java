package com.prokopovich.persondata.service;

import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.SerializationObject;
import com.prokopovich.persondata.util.exception.InvalidUrlException;
import com.prokopovich.persondata.util.validator.HttpResponseValidator;
import com.prokopovich.persondata.util.validator.UriValidator;
import com.prokopovich.persondata.util.webclient.CustomHttpClient;
import com.prokopovich.persondata.util.webclient.CustomHttpResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UrlService {

    private static final Logger LOGGER = LogManager.getLogger(UrlService.class);

    private final Scanner in = new Scanner(System.in);

    private final PersonService personService = new PersonService();

    public void startConsoleApp() {
        while (true) {
            Person person = getDataFromUrl();
            if (person != null) {
                displayData(person);
            }
            System.out.print("\nGet more data from the link?" +
                    "\n\tYES - 1;" +
                    "\n\tNO - any symbol" +
                    "\nYour choice: ");
            String choice = in.nextLine();
            if(!choice.equals("1")) {
                return;
            }
        }
    }

    private Person getDataFromUrl() {
        URL url = enterUrl();
        Person person = null;
        CustomHttpClient httpClient = new CustomHttpClient();
        try {
            CustomHttpResponse httpResponse = httpClient.getData(url);
            HttpResponseValidator validator = new HttpResponseValidator();
            validator.checkHttpResponse(httpResponse);
            person = personService.constructPersonObject(httpResponse.getBody());

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
        }
        return person;
    }

    private URL enterUrl() {
        System.out.print("Enter the URI: ");
        String uriStr = in.nextLine();
        UriValidator validator = new UriValidator();
        URL url = null;
        try {
            validator.checkEnteredUrl(uriStr);
            url = new URL(uriStr);
        } catch (InvalidUrlException | MalformedURLException e) {
            LOGGER.error(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
        }
        return url;
    }

    private void displayData(Person person) {
        try {
            File file = personService.getDataToDisplay(person);
            person = SerializationObject.deserializeObject(file);
            System.out.println(person);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
