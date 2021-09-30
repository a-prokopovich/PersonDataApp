package com.prokopovich.persondata.webclient.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HttpResponse {

    private int statusCode;

    private byte[] body;

    @Override
    public String toString() {
        return "HttpResponse: " +
            "statusCode = " + statusCode +
            ", body = " + new String(body) + ';';
    }
}
