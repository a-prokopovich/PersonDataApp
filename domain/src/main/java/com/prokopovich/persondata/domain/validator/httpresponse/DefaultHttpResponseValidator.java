package com.prokopovich.persondata.domain.validator.httpresponse;

import com.prokopovich.persondata.domain.exception.HttpResponseException;
import com.prokopovich.persondata.webclient.response.HttpResponse;

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
            if (httpResponse.getBody() == null) throw new HttpResponseException("Response body is empty");
        }
    }
}
