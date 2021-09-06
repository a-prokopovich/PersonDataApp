package com.prokopovich.persondata.view.servlet.filter;

import com.prokopovich.persondata.view.servlet.wrapper.ServletResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompressionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        var acceptEncoding = httpRequest.getHeader("Accept-Encoding");

        if (acceptEncoding != null) {

            if (acceptEncoding.contains("deflate") && !acceptEncoding.contains("gzip")) {

                var deflateResponseWrapper = new ServletResponseWrapper(httpResponse, "deflate");

                chain.doFilter(request, deflateResponseWrapper);
                httpResponse.addHeader("Content-Encoding", "deflate");
                deflateResponseWrapper.finishResponse();

            } else if (acceptEncoding.contains("gzip")) {

                var gzipResponseWrapper = new ServletResponseWrapper(httpResponse, "gzip");

                chain.doFilter(request, gzipResponseWrapper);
                httpResponse.addHeader("Content-Encoding", "gzip");
                gzipResponseWrapper.finishResponse();
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
