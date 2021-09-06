package com.prokopovich.persondata.view.servlet.wrapper.stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

public class DeflateResponseStream extends ServletOutputStream {

    private DeflaterOutputStream deflateStream;
    private OutputStream output;

    public DeflateResponseStream(OutputStream output) {
        this.output = output;
        deflateStream = new DeflaterOutputStream(output);
    }

    @Override
    public void write(byte b[]) throws IOException {
        deflateStream.write(b, 0, b.length);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        deflateStream.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        deflateStream.write(b);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}