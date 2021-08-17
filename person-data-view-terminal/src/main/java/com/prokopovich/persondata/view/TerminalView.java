package com.prokopovich.persondata.view;

import com.prokopovich.persondata.service.api.PersonService;
import com.prokopovich.persondata.service.exception.PersonServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Scanner;

@RequiredArgsConstructor
public class TerminalView implements ViewApp {

    private static final Logger LOGGER = LogManager.getLogger(TerminalView.class);
    private final Scanner in = new Scanner(System.in);

    private final PersonService urlService;

    @Override
    public void displayStartWindow() {
        LOGGER.trace("displayed start window");

        while (true) {
            System.out.print("Enter the URL: ");
            String url = in.nextLine();

            try {
                String personInfo = urlService.getDataFromUrl(url);
                System.out.println("personInfo from URL: \n" + personInfo);
            } catch (PersonServiceException e) {
                LOGGER.error(e.getMessage(), e.getCause());
                System.out.println("Exception: " + e.getMessage());
            }

            System.out.print("\nGet more data from the URL?" +
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
