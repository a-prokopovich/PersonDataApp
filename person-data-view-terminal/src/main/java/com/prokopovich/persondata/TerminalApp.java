package com.prokopovich.persondata;

import com.prokopovich.persondata.model.validator.EmailValidator;
import com.prokopovich.persondata.model.validator.PassportDataValidator;
import com.prokopovich.persondata.model.validator.PassportNumberValidator;
import com.prokopovich.persondata.model.validator.PersonValidator;
import com.prokopovich.persondata.model.validator.PhoneValidator;
import com.prokopovich.persondata.model.validator.RequiredFieldValidator;
import com.prokopovich.persondata.parser.api.PersonParser;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.httpclient.DefaultHttpClient;
import com.prokopovich.persondata.parser.jsonparser.JsonPersonParser;
import com.prokopovich.persondata.service.DefaultPersonConstructor;
import com.prokopovich.persondata.service.DefaultPersonModifier;
import com.prokopovich.persondata.service.DefaultPersonService;
import com.prokopovich.persondata.service.api.PersonConstructor;
import com.prokopovich.persondata.service.api.PersonModifier;
import com.prokopovich.persondata.service.api.PersonService;
import com.prokopovich.persondata.service.validator.DefaultEnteredUrlValidator;
import com.prokopovich.persondata.service.validator.EnteredUrlValidator;
import com.prokopovich.persondata.view.TerminalView;
import com.prokopovich.persondata.webclient.validator.DefaultHttpResponseValidator;
import com.prokopovich.persondata.webclient.validator.HttpResponseValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class TerminalApp {

    private static final Logger LOGGER = LogManager.getLogger(TerminalApp.class);

    public static void main( String[] args ) {

        final PassportNumberValidator passportNumberValidator = new PassportNumberValidator();
        final RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        final PhoneValidator phoneValidator = new PhoneValidator();
        final EmailValidator emailValidator = new EmailValidator();

        final PassportDataValidator passportDataValidator = new PassportDataValidator(passportNumberValidator, requiredFieldValidator);
        final PersonValidator personValidator = new PersonValidator(phoneValidator, emailValidator, passportDataValidator, requiredFieldValidator);

        final HttpResponseValidator httpResponseValidator = new DefaultHttpResponseValidator();
        final EnteredUrlValidator urlValidator = new DefaultEnteredUrlValidator();

        final PersonParser parser = new JsonPersonParser();

        final PersonConstructor personConstructor = new DefaultPersonConstructor(parser, personValidator);
        final PersonModifier personModifier = new DefaultPersonModifier();


        final HttpClient httpClient = new DefaultHttpClient();

        final PersonService personService = new DefaultPersonService(httpClient, personConstructor,
                personModifier, urlValidator, httpResponseValidator);

        LOGGER.info("terminal application is started");

        TerminalView terminalView = new TerminalView(personService);
        terminalView.displayStartWindow();

        LOGGER.info("terminal application completed successfully");
    }
}
