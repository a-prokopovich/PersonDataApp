package com.prokopovich.persondata.view;

import com.prokopovich.persondata.service.UrlService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class TerminalView implements ViewApp {

    private static final Logger LOGGER = LogManager.getLogger(TerminalView.class);
    private final Scanner in = new Scanner(System.in);

    private final UrlService urlService = new UrlService();

    @Override
    public void displayStartWindow() {
        while (true) {
            System.out.print("Enter the URI: ");
            String urlStr = in.nextLine();
            try {
                String personInfo = urlService.getDataFromUrl(urlStr);
                System.out.println("personInfo from URI: \n" + personInfo);
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.error(e.getMessage());
                System.out.println("Exception: " + e.getMessage());
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
}
