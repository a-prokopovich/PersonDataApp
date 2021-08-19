package com.prokopovich.persondata.webclient.validator;

import com.prokopovich.persondata.webclient.exception.HttpResponseException;
import com.prokopovich.persondata.webclient.response.HttpResponse;

public interface HttpResponseValidator {

    /**
     * @throws HttpResponseException if HTTP response error
     */
    void checkHttpResponse(HttpResponse httpResponse);
}
