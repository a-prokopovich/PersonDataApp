package com.prokopovich.persondata;

import com.prokopovich.persondata.cache.PersonListCache;
import com.prokopovich.persondata.domain.service.DefaultPersonConstructor;
import com.prokopovich.persondata.domain.validator.EmailValidator;
import com.prokopovich.persondata.domain.validator.PassportDataValidator;
import com.prokopovich.persondata.domain.validator.PassportNumberValidator;
import com.prokopovich.persondata.domain.validator.PersonValidator;
import com.prokopovich.persondata.domain.validator.PhoneValidator;
import com.prokopovich.persondata.domain.validator.RequiredFieldValidator;
import com.prokopovich.persondata.parser.jsonparser.JsonPersonParser;
import com.prokopovich.persondata.domain.service.DefaultPersonModifier;
import com.prokopovich.persondata.service.DefaultPersonService;
import com.prokopovich.persondata.service.validator.DefaultInUrlValidator;
import com.prokopovich.persondata.view.terminal.TerminalView;
import com.prokopovich.persondata.webclient.httpclient.DefaultHttpClient;
import com.prokopovich.persondata.service.validator.DefaultHttpResponseValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonDataApp {

    public static void main( String[] args ) {

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


        final var httpClient = new DefaultHttpClient();
        final var personList = new PersonListCache(3);

        final var personService = new DefaultPersonService(httpClient, personConstructor,
            personModifier, personValidator, urlValidator, httpResponseValidator, personList);

        log.info("Application is started");

        TerminalView terminalView = new TerminalView(personService);
        terminalView.displayStartWindow();

        log.info("Application completed successfully");
    }
}
