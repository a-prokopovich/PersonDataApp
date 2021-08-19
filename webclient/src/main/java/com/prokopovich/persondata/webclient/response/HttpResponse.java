package com.prokopovich.persondata.webclient.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.InputStream;

@Data
@Getter
@Setter
@AllArgsConstructor
public class HttpResponse {

    private int statusCode;

    private InputStream body;

    @Override
    public String toString() {
        return "HttpResponse{" +
            "statusCode=" + statusCode +
            '}';
    }
}
