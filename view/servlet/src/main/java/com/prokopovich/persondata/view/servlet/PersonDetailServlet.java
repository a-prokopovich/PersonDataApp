package com.prokopovich.persondata.view.servlet;

import com.google.gson.Gson;
import com.prokopovich.persondata.domain.service.parser.JsonPersonParser;
import com.prokopovich.persondata.domain.service.PersonService;
import com.prokopovich.persondata.domain.exception.PersonServiceException;
import com.prokopovich.persondata.view.servlet.data.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class PersonDetailServlet extends HttpServlet {

    private final PersonService personService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        var writer = response.getWriter();

        try {
            var person = personService.getById(getIdByPatch(request));

            var personJson = new Gson().toJson(person);
            writer.print(personJson);
            response.setStatus(200);

        } catch (PersonServiceException e) {
            log.error(e.getMessage(), e.getCause());

            var responseJson = new MessageResponse(e.getMessage());
            var messageJson = new Gson().toJson(responseJson);
            writer.print(messageJson);
            response.setStatus(e.getStatusCode());

        }
        writer.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        var writer = response.getWriter();
        var responseJson = new MessageResponse();

        try {
            JsonPersonParser parser = new JsonPersonParser();
            var person = parser.parse(IOUtils.toByteArray(request.getInputStream()));

            personService.update(
                getIdByPatch(request),
                person
            );

            responseJson.setMessage("Person updated successfully");
            response.setStatus(200);

        } catch (PersonServiceException e) {
            log.error(e.getMessage(), e.getCause());

            responseJson.setMessage(e.getMessage());
            response.setStatus(e.getStatusCode());
        }
        var messageJson = new Gson().toJson(responseJson);
        writer.print(messageJson);
        writer.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        var writer = response.getWriter();
        var responseJson = new MessageResponse();

        try {
            personService.delete(getIdByPatch(request));

            responseJson.setMessage("Person deleted successfully");
            response.setStatus(200);

        } catch (PersonServiceException e) {
            log.error(e.getMessage(), e.getCause());

            responseJson.setMessage(e.getMessage());
            response.setStatus(e.getStatusCode());
        }
        var messageJson = new Gson().toJson(responseJson);
        writer.print(messageJson);
        writer.flush();
    }

    private int getIdByPatch(HttpServletRequest request) {

        String[] pathInfoParts = request.getPathInfo().split("/");
        try {
            int id = Integer.parseInt(pathInfoParts[1]);

            return id;
        } catch (NumberFormatException e) {
            throw new PersonServiceException("Invalid Id", 400);
        }
    }
}
