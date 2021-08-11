package com.prokopovich.persondata.webclient.validator;

import com.prokopovich.persondata.webclient.exception.HttpResponseException;
import com.prokopovich.persondata.webclient.HttpResponse;

import java.io.IOException;

public class DefaultHttpResponseValidator implements HttpResponseValidator {

    @Override
    public void checkHttpResponse(HttpResponse httpResponse) {
        if (httpResponse.getStatusCode() > 399 && httpResponse.getStatusCode() < 500) {
            throw new HttpResponseException("HTTP client error");
        }
        if (httpResponse.getStatusCode() > 499 && httpResponse.getStatusCode() < 600) {
            throw new HttpResponseException("HTTP server error");
        }
        if (httpResponse.getStatusCode() == 200) {
            try {
                if (httpResponse.getBody().available() == 0) {
                    throw new HttpResponseException("Response body is empty");
                }
            } catch (IOException e) {
                throw new HttpResponseException("Exception with InputStream in method available()");
            }
        }
    }
}
