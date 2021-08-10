package com.prokopovich.persondata;

import com.prokopovich.persondata.service.UrlService;

public class PersonDataApp {

    public static void main(String [] args) {
        UrlService guiService = new UrlService();
        guiService.startConsoleApp();
    }
}
