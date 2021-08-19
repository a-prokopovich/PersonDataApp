package com.prokopovich.persondata.view.terminal;

import com.prokopovich.persondata.service.PersonService;
import com.prokopovich.persondata.service.exception.PersonServiceException;
import com.prokopovich.persondata.view.View;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
public class TerminalView implements View {

    private final Scanner in = new Scanner(System.in);

    private final PersonService urlService;

    @Override
    public void displayStartWindow() {

        log.info("Displayed start window");

        while (true) {
            System.out.print("Enter the URL: ");
            String url = in.nextLine();

            try {
                var person = urlService.getByUrl(url);
                System.out.println("personInfo from URL: \n" + person);

            } catch (PersonServiceException e) {
                log.error(e.getMessage(), e.getCause());

                System.out.println("\nException: " + e.getMessage());
            }

            System.out.print("\nGet more data from the URL?" +
                    "\n\tYES - 1;" +
                    "\n\tNO - any symbol" +
                    "\nYour choice: ");
            String choice = in.nextLine();
            if (!choice.equals("1")) {
                return;
            }
        }
    }
}
