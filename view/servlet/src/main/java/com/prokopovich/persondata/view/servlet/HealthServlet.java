package com.prokopovich.persondata.view.servlet;

import com.google.gson.Gson;
import com.prokopovich.persondata.view.servlet.data.HealthResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HealthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var responseBody = new HealthResponse("UP");
        var responseJson = new Gson().toJson(responseBody);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(200);

        var writer = response.getWriter();
        writer.print(responseJson);
        writer.flush();
    }
}
