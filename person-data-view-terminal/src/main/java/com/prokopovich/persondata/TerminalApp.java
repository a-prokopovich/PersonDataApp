package com.prokopovich.persondata;

import com.prokopovich.persondata.model.validator.DefaultModelDataValidator;
import com.prokopovich.persondata.model.validator.ModelDataValidator;
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


public class TerminalApp {
    public static void main( String[] args )
    {
        final HttpResponseValidator httpResponseValidator = new DefaultHttpResponseValidator();
        final ModelDataValidator modelDataValidator = new DefaultModelDataValidator();
        final EnteredUrlValidator urlValidator = new DefaultEnteredUrlValidator();

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
