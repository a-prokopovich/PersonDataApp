package com.prokopovich.persondata.webapp.filter.wrapper;

import com.prokopovich.persondata.webapp.filter.stream.DeflateResponseStream;
import com.prokopovich.persondata.webapp.filter.stream.GZIPResponseStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ServletResponseWrapper extends HttpServletResponseWrapper {

    protected HttpServletResponse response;
    protected ServletOutputStream stream = null;
    protected PrintWriter writer = null;

    private final String acceptEncoding;


    public ServletResponseWrapper(HttpServletResponse response, String acceptEncoding) {
        super(response);
        this.response = response;
        this.acceptEncoding = acceptEncoding;
    }

    public ServletOutputStream createOutputStream() throws IOException {

        if (acceptEncoding.equals("gzip")) {
            return new GZIPResponseStream(response.getOutputStream());

        } else if (acceptEncoding.equals("deflate")) {
            return new DeflateResponseStream(response.getOutputStream());
        }
        return null;
    }

    public void finishResponse() throws IOException {
        if (writer != null) {
            writer.close();
        } else {
            if (stream != null) {
                stream.close();
            }
        }
    }

    public void flushBuffer() throws IOException {

        if (writer != null) {
            writer.flush();
        }

        if (stream != null) {
            stream.flush();
        }
    }

    public ServletOutputStream getOutputStream() throws IOException {

        if (writer != null) {
            throw new IllegalStateException("printWriter already defined");
        }

        if (stream == null) {
            stream = createOutputStream();
        }
        return stream;
    }

    public PrintWriter getWriter() throws IOException {

        if (stream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }
        if (writer == null) {
            stream = createOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8));
        }
        return writer;
    }
}