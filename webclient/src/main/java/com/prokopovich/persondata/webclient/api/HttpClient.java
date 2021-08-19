package com.prokopovich.persondata.webclient.api;

import com.prokopovich.persondata.webclient.response.HttpResponse;
import com.prokopovich.persondata.webclient.exception.HttpClientException;

public interface HttpClient {

    /**
     * @throws HttpClientException if connection error
     */
    HttpResponse getData(String url);
}
