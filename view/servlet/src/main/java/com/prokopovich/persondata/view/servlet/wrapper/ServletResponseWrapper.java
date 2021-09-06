package com.prokopovich.persondata.view.servlet.wrapper;

import com.prokopovich.persondata.view.servlet.wrapper.stream.DeflateResponseStream;
import com.prokopovich.persondata.view.servlet.wrapper.stream.GZIPResponseStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
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
        stream.flush();
    }


    public PrintWriter getWriter() throws IOException {

        if (writer != null) {
            return writer;
        }

        if (stream != null) {
            throw new IllegalStateException("getOutputStream() has already been called!");
        }

        stream = createOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8));
        return writer;
    }

    public ServletOutputStream getOutputStream() throws IOException {

        if (writer != null) {
            throw new IllegalStateException("printWriter already defined");
        }

        if (stream == null)
            stream = createOutputStream();
        return stream;
    }
}
