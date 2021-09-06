package com.prokopovich.persondata.view.servlet;

import com.google.gson.Gson;
import com.prokopovich.persondata.service.PersonService;
import com.prokopovich.persondata.service.exception.PersonServiceException;
import com.prokopovich.persondata.view.servlet.data.MessageResponse;
import com.prokopovich.persondata.view.servlet.data.UrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class PersonsServlet extends HttpServlet {

    private final PersonService personService;
    //private static final Logger log = LogManager.getLogger(PersonsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("HELLO");

        response.setContentType("application/json;charset=UTF-8");
        var writer = response.getWriter();

        try {
            var responseJson = new Gson().toJson(personService.getListByCache());
            writer.print(responseJson);
            response.setStatus(200);

        } catch (PersonServiceException e) {
            log.error("Exception: " + e.getMessage(), e.getCause());

            var responseJson = new MessageResponse("Exception: " + e.getMessage());
            var messageJson = new Gson().toJson(responseJson);
            writer.print(messageJson);
            response.setStatus(e.getStatusCode());
        }
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        var writer = response.getWriter();

        try {
            var url = getUrlByBody(request);
            var person = personService.getByUrl(url);

            personService.putToListCache(person);

            var personJson = new Gson().toJson(person);

            writer.print(personJson);
            response.setStatus(200);

        } catch (PersonServiceException e) {
            log.error(e.getMessage(), e.getCause());

            var responseJson = new MessageResponse("Exception: " + e.getMessage());
            var messageJson = new Gson().toJson(responseJson);
            writer.print(messageJson);
            response.setStatus(e.getStatusCode());
        }
        writer.flush();
    }

    private String getUrlByBody(HttpServletRequest request) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            var urlBody = new Gson().fromJson(
                sb.toString(),
                UrlRequest.class);

            return urlBody.getUrl();
        }
    }
}
