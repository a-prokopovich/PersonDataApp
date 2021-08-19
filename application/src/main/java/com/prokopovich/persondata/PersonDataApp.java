package com.prokopovich.persondata;

import com.prokopovich.persondata.domain.service.DefaultPersonConstructor;
import com.prokopovich.persondata.domain.validator.EmailValidator;
import com.prokopovich.persondata.domain.validator.PassportDataValidator;
import com.prokopovich.persondata.domain.validator.PassportNumberValidator;
import com.prokopovich.persondata.domain.validator.PersonValidator;
import com.prokopovich.persondata.domain.validator.PhoneValidator;
import com.prokopovich.persondata.domain.validator.RequiredFieldValidator;
import com.prokopovich.persondata.parser.api.PersonParser;
import com.prokopovich.persondata.parser.jsonparser.JsonPersonParser;
import com.prokopovich.persondata.domain.service.DefaultPersonModifier;
import com.prokopovich.persondata.service.DefaultPersonService;
import com.prokopovich.persondata.service.PersonService;
import com.prokopovich.persondata.domain.service.PersonConstructor;
import com.prokopovich.persondata.domain.service.PersonModifier;
import com.prokopovich.persondata.service.validator.DefaultInUrlValidator;
import com.prokopovich.persondata.service.validator.InUrlValidator;
import com.prokopovich.persondata.view.terminal.TerminalView;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.httpclient.DefaultHttpClient;
import com.prokopovich.persondata.webclient.validator.DefaultHttpResponseValidator;
import com.prokopovich.persondata.webclient.validator.HttpResponseValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonDataApp {

    public static void main( String[] args ) {

        final PassportNumberValidator passportNumberValidator = new PassportNumberValidator();
        final RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        final PhoneValidator phoneValidator = new PhoneValidator();
        final EmailValidator emailValidator = new EmailValidator();

        final PassportDataValidator passportDataValidator = new PassportDataValidator(passportNumberValidator, requiredFieldValidator);
        final PersonValidator personValidator = new PersonValidator(phoneValidator, emailValidator, passportDataValidator, requiredFieldValidator);

        final HttpResponseValidator httpResponseValidator = new DefaultHttpResponseValidator();
        final InUrlValidator urlValidator = new DefaultInUrlValidator();

        final PersonParser parser = new JsonPersonParser();

        final PersonConstructor personConstructor = new DefaultPersonConstructor(parser, personValidator);
        final PersonModifier personModifier = new DefaultPersonModifier();


        final HttpClient httpClient = new DefaultHttpClient();

        final PersonService personService = new DefaultPersonService(httpClient, personConstructor,
            personModifier, urlValidator, httpResponseValidator);

        log.info("application is started");

        TerminalView terminalView = new TerminalView(personService);
        terminalView.displayStartWindow();

        log.info("application completed successfully");
    }
}
