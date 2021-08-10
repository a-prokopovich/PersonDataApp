package com.prokopovich.persondata;

import com.prokopovich.persondata.service.PersonService;
import com.prokopovich.persondata.service.UrlService;
import com.prokopovich.persondata.util.webclient.CustomClient;
import com.prokopovich.persondata.util.webclient.CustomHttpClient;
import com.prokopovich.persondata.view.TerminalView;

public class PersonDataApp {

    public static void main(String [] args) {

        final PersonService personService = new PersonService();
        final CustomClient httpClient = new CustomHttpClient();
        final UrlService urlService = new UrlService(httpClient, personService);

        TerminalView terminalView = new TerminalView(urlService);
        terminalView.displayStartWindow();
    }
}
