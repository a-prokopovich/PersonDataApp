package com.prokopovich.persondata.webclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Data
@Getter
@Setter
@AllArgsConstructor
public class HttpResponse {

    private int statusCode;

    private InputStream body;
}
