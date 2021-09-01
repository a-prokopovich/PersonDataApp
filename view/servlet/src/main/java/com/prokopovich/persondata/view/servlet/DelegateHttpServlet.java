package com.prokopovich.persondata.view.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class DelegateHttpServlet extends HttpServlet {
    private static final String SERVLET_CONTEXT_KEY_INIT_PARAMETER = "servletContextKey";

    private HttpServlet mServlet;

    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        locateServlet(servletConfig);
        mServlet.init(servletConfig);
    }

    private void locateServlet(ServletConfig servletConfig) throws ServletException {

        String servletContextAttributeName = servletConfig.getInitParameter(SERVLET_CONTEXT_KEY_INIT_PARAMETER);
        if (servletContextAttributeName == null) {
            throw new ServletException("Unable to find init parameter '" + SERVLET_CONTEXT_KEY_INIT_PARAMETER + "'");
        }

        Object object = servletConfig.getServletContext().getAttribute(servletContextAttributeName);

        if (object == null) {
            throw new ServletException("Unable to find " + servletContextAttributeName + " in servlet context.");
        }

        if (!(object instanceof HttpServlet)) {
            throw new ServletException("Object is not an instance of"
                + HttpServlet.class.getName()
                + ".  Class is "
                + object.getClass().getName()
                + ".");
        }

        mServlet = (HttpServlet) object;
    }

    public void destroy() {
        mServlet.destroy();
    }

    public void service(ServletRequest request, ServletResponse response)
        throws ServletException, IOException {

        mServlet.service(request, response);
    }
}
