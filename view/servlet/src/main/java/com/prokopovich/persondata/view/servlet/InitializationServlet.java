package com.prokopovich.persondata.view.servlet;

import com.prokopovich.persondata.cache.PersonListCache;
import com.prokopovich.persondata.domain.service.DefaultPersonConstructor;
import com.prokopovich.persondata.domain.service.DefaultPersonModifier;
import com.prokopovich.persondata.domain.validator.EmailValidator;
import com.prokopovich.persondata.domain.validator.PassportDataValidator;
import com.prokopovich.persondata.domain.validator.PassportNumberValidator;
import com.prokopovich.persondata.domain.validator.PersonValidator;
import com.prokopovich.persondata.domain.validator.PhoneValidator;
import com.prokopovich.persondata.domain.validator.RequiredFieldValidator;
import com.prokopovich.persondata.parser.jsonparser.JsonPersonParser;
import com.prokopovich.persondata.service.DefaultPersonService;
import com.prokopovich.persondata.service.PersonService;
import com.prokopovich.persondata.service.validator.DefaultHttpResponseValidator;
import com.prokopovich.persondata.service.validator.DefaultInUrlValidator;
import com.prokopovich.persondata.webclient.httpclient.DefaultHttpClient;

public class InitializationServlet extends HealthServlet {

    @Override
    public void init() {

        var personService = initialisePersonService();

        var personsServlet = new PersonsServlet(personService);
        var personDetailServlet = new PersonDetailServlet(personService);
        var healthServlet = new HealthServlet();

        getServletContext().setAttribute("personsServlet", personsServlet);
        getServletContext().setAttribute("personDetailServlet", personDetailServlet);
        getServletContext().setAttribute("healthServlet", healthServlet);
    }

    private PersonService initialisePersonService() {

        final var passportNumberValidator = new PassportNumberValidator();
        final var requiredFieldValidator = new RequiredFieldValidator();
        final var phoneValidator = new PhoneValidator();
        final var emailValidator = new EmailValidator();

        final var passportDataValidator = new PassportDataValidator(passportNumberValidator, requiredFieldValidator);
        final var personValidator = new PersonValidator(phoneValidator, emailValidator, passportDataValidator, requiredFieldValidator);

        final var httpResponseValidator = new DefaultHttpResponseValidator();
        final var urlValidator = new DefaultInUrlValidator();

        final var parser = new JsonPersonParser();

        final var personConstructor = new DefaultPersonConstructor(parser, personValidator);
        final var personModifier = new DefaultPersonModifier();

        final PersonListCache personList = new PersonListCache(3);
        final var httpClient = new DefaultHttpClient();

        return new DefaultPersonService(httpClient, personConstructor,
            personModifier, personValidator, urlValidator, httpResponseValidator, personList);
    }
}
