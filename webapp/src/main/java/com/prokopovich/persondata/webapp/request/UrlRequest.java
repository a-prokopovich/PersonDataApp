package com.prokopovich.persondata.webapp.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UrlRequest {

    @URL
    private String url;
}
