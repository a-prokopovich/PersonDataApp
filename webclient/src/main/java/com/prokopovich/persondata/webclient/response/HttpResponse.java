package com.prokopovich.persondata.webclient.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
            ", body = " + new String(body) + ';';
    }
}
