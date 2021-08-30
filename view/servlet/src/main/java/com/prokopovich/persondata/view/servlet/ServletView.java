package com.prokopovich.persondata.view.servlet;

import com.prokopovich.persondata.domain.service.DefaultPersonConstructor;
import com.prokopovich.persondata.domain.service.DefaultPersonModifier;
import com.prokopovich.persondata.domain.service.PersonConstructor;
import com.prokopovich.persondata.domain.service.PersonModifier;
import com.prokopovich.persondata.domain.validator.EmailValidator;
import com.prokopovich.persondata.domain.validator.PassportDataValidator;
import com.prokopovich.persondata.domain.validator.PassportNumberValidator;
import com.prokopovich.persondata.domain.validator.PersonValidator;
import com.prokopovich.persondata.domain.validator.PhoneValidator;
import com.prokopovich.persondata.domain.validator.RequiredFieldValidator;
import com.prokopovich.persondata.parser.api.PersonParser;
import com.prokopovich.persondata.parser.jsonparser.JsonPersonParser;
import com.prokopovich.persondata.service.DefaultPersonService;
import com.prokopovich.persondata.service.PersonService;
import com.prokopovich.persondata.service.exception.PersonServiceException;
import com.prokopovich.persondata.service.validator.DefaultHttpResponseValidator;
import com.prokopovich.persondata.service.validator.DefaultInUrlValidator;
import com.prokopovich.persondata.service.validator.HttpResponseValidator;
import com.prokopovich.persondata.service.validator.InUrlValidator;
import com.prokopovich.persondata.webclient.api.HttpClient;
import com.prokopovich.persondata.webclient.httpclient.DefaultHttpClient;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/persons")
public class ServletView extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private PersonService personService;

    @Override
    public void init(ServletConfig config) throws ServletException {

        PassportNumberValidator passportNumberValidator = new PassportNumberValidator();
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        PhoneValidator phoneValidator = new PhoneValidator();
        EmailValidator emailValidator = new EmailValidator();

        PassportDataValidator passportDataValidator = new PassportDataValidator(passportNumberValidator, requiredFieldValidator);
        PersonValidator personValidator = new PersonValidator(phoneValidator, emailValidator, passportDataValidator, requiredFieldValidator);

        HttpResponseValidator httpResponseValidator = new DefaultHttpResponseValidator();
        InUrlValidator urlValidator = new DefaultInUrlValidator();

        PersonParser parser = new JsonPersonParser();

        PersonConstructor personConstructor = new DefaultPersonConstructor(parser, personValidator);
        PersonModifier personModifier = new DefaultPersonModifier();


        HttpClient httpClient = new DefaultHttpClient();

        this.personService = new DefaultPersonService(httpClient, personConstructor,
            personModifier, urlValidator, httpResponseValidator);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        var url = request.getParameter("url");
        request.setAttribute("url", url);

        try {
            var person = personService.getByUrl(url);
            request.setAttribute("id", person.getId());
            request.setAttribute("fullName", person.getFullName());
            request.setAttribute("phone", person.getPhone());
            request.setAttribute("email", person.getEmail());

            System.out.println("personInfo from URL: \n" + person);

        } catch (PersonServiceException e) {
            request.setAttribute("message", "Exception: " + e.getMessage());
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
