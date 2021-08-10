package com.prokopovich.persondata.util.webclient;

import java.io.IOException;
import java.net.URL;

public interface CustomClient {

    CustomResponse getData(URL url) throws IOException;
}
