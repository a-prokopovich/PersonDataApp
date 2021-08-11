package com.prokopovich.persondata.webclient;

import com.prokopovich.persondata.webclient.exception.HttpClientException;

import java.net.URL;

public interface HttpClient {

    /**
     * @throws HttpClientException if connection error
     */
    HttpResponse getData(URL url);
}
