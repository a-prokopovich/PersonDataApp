package com.prokopovich.persondata.view.servlet.wrapper.stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPResponseStream extends ServletOutputStream {

    private GZIPOutputStream gzipStream;
    private OutputStream output;

    public GZIPResponseStream(OutputStream output) throws IOException {
        this.output = output;
        gzipStream = new GZIPOutputStream(output);
    }

    @Override
    public void write(byte b[]) throws IOException {
        gzipStream.write(b, 0, b.length);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        gzipStream.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        gzipStream.write(b);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}