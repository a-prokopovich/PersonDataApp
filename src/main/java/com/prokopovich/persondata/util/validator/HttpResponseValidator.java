package com.prokopovich.persondata.util.validator;

import com.prokopovich.persondata.util.exception.HttpResponseException;
import com.prokopovich.persondata.util.webclient.CustomResponse;

import java.io.IOException;

public class HttpResponseValidator {

    public void checkHttpResponse(CustomResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode() > 399 && httpResponse.getStatusCode() < 500) {
            throw new HttpResponseException("Http Client error");
        }
        if (httpResponse.getStatusCode() > 499 && httpResponse.getStatusCode() < 600) {
            throw new HttpResponseException("Http Server error");
        }
        if (httpResponse.getStatusCode() == 200) {
            if (httpResponse.getBody().available() == 0) {
                throw new HttpResponseException("Response body is empty");
            }
        }
    }
}
