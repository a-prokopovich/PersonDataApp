package com.prokopovich.persondata;

import com.prokopovich.persondata.service.DefaultPersonService;
import com.prokopovich.persondata.service.PersonService;
import com.prokopovich.persondata.service.person.DefaultPersonConstructor;
import com.prokopovich.persondata.service.person.DefaultPersonModifier;
import com.prokopovich.persondata.service.person.PersonConstructor;
import com.prokopovich.persondata.service.person.PersonModifier;
import com.prokopovich.persondata.util.parser.JsonPersonParser;
import com.prokopovich.persondata.util.parser.PersonParser;
import com.prokopovich.persondata.webclient.validator.DefaultHttpResponseValidator;
import com.prokopovich.persondata.util.validator.DefaultModelDataValidator;
import com.prokopovich.persondata.util.validator.DefaultEnteredUrlValidator;
import com.prokopovich.persondata.webclient.HttpClient;
import com.prokopovich.persondata.webclient.DefaultHttpClient;
import com.prokopovich.persondata.view.TerminalView;

public class PersonDataApp {

    public static void main(String [] args) {

        final DefaultHttpResponseValidator httpResponseValidator = new DefaultHttpResponseValidator();
        final DefaultModelDataValidator modelDataValidator = new DefaultModelDataValidator();
        final DefaultEnteredUrlValidator urlValidator = new DefaultEnteredUrlValidator();

        final PersonParser parser = new JsonPersonParser();

        final PersonConstructor personConstructor = new DefaultPersonConstructor(parser, modelDataValidator);
        final PersonModifier personModifier = new DefaultPersonModifier();


        final HttpClient httpClient = new DefaultHttpClient();

        final PersonService personService = new DefaultPersonService(httpClient, personConstructor,
                                                    personModifier, urlValidator, httpResponseValidator);

        TerminalView terminalView = new TerminalView(personService);
        terminalView.displayStartWindow();
    }
}
