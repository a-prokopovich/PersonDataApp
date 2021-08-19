package com.prokopovich.persondata.service.validator;

import com.prokopovich.persondata.service.exception.HttpResponseException;
import com.prokopovich.persondata.webclient.response.HttpResponse;

public interface HttpResponseValidator {

    /**
     * @throws HttpResponseException if HTTP response error
     */
    void checkHttpResponse(HttpResponse httpResponse);
}
