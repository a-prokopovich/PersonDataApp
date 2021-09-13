package com.prokopovich.persondata.domain.validator.httpresponse;

import com.prokopovich.persondata.domain.exception.HttpResponseException;
import com.prokopovich.persondata.webclient.response.HttpResponse;

public interface HttpResponseValidator {

    /**
     * @throws HttpResponseException if HTTP response error
     */
    void checkHttpResponse(HttpResponse httpResponse);
}
