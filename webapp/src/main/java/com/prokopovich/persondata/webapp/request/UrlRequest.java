package com.prokopovich.persondata.webapp.request;

import com.prokopovich.persondata.domain.validator.inurl.ValidInUrl;
import lombok.Data;

@Data
public class UrlRequest {

    @ValidInUrl
    private String url;
}
