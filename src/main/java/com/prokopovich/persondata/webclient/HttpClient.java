package com.prokopovich.persondata.webclient;

import com.prokopovich.persondata.webclient.exception.HttpClientException;

public interface HttpClient {

    /**
     * @throws HttpClientException if connection error
     */
    HttpResponse getData(String url);
}
