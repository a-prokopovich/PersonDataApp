package com.prokopovich.persondata.webclient.validator;

import com.prokopovich.persondata.webclient.HttpResponse;
import com.prokopovich.persondata.webclient.exception.HttpResponseException;

public interface HttpResponseValidator {

    /**
     * @throws HttpResponseException if HTTP response error
     */
    void checkHttpResponse(HttpResponse httpResponse);
}
