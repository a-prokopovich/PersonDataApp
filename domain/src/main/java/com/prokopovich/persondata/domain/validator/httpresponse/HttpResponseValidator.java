package com.prokopovich.persondata.domain.validator.httpresponse;

import com.prokopovich.persondata.webclient.response.HttpResponse;
import com.prokopovich.persondata.domain.exception.HttpResponseException;

public interface HttpResponseValidator {

    /**
     * @throws HttpResponseException if HTTP response error
     */
    void checkHttpResponse(HttpResponse httpResponse);
}
