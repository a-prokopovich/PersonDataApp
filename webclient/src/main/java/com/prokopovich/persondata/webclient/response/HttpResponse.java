package com.prokopovich.persondata.webclient.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.InputStream;
import java.util.Arrays;

@Data
@AllArgsConstructor
@Builder
public class HttpResponse {

    private final int statusCode;

    private final byte[] body;

    @Override
    public String toString() {
        return "HttpResponse: " +
            "statusCode = " + statusCode +
            "body = " + Arrays.toString(body) + ';';
    }
}
