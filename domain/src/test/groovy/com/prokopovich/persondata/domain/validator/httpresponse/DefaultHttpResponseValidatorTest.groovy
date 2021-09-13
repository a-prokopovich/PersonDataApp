package com.prokopovich.persondata.domain.validator.httpresponse

import com.prokopovich.persondata.webclient.response.HttpResponse
import com.prokopovich.persondata.domain.exception.HttpResponseException
import spock.lang.Specification

class DefaultHttpResponseValidatorTest extends Specification {

    def httpResponseValidator = new DefaultHttpResponseValidator()

    def "should not throw exceptions in case of valid response"() {
        given:
            def response = new HttpResponse(200, "body".getBytes())

        when:
            httpResponseValidator.checkHttpResponse(response)

        then:
            notThrown HttpResponseException
    }

    def "should throw HttpResponseException in case of HTTP client error"() {
        when:
            httpResponseValidator.checkHttpResponse(response)

        then:
            def e = thrown HttpResponseException
            e.getMessage().contains("HTTP client error")

        where:
            response << [
                new HttpResponse(400, "body".getBytes()),
                new HttpResponse(410, null),
                new HttpResponse(420, "body".getBytes()),
                new HttpResponse(430, null),
                new HttpResponse(444, "body".getBytes()),
                new HttpResponse(450, null),
                new HttpResponse(499, "body".getBytes())
            ]
    }

    def "should throw HttpResponseException in case of HTTP server error"() {
        when:
            httpResponseValidator.checkHttpResponse(response)

        then:
            def e = thrown HttpResponseException
            e.getMessage().contains("HTTP server error")

        where:
            response << [
                new HttpResponse(500, "body".getBytes()),
                new HttpResponse(503, null),
                new HttpResponse(505, "body".getBytes()),
                new HttpResponse(530, null),
                new HttpResponse(544, "body".getBytes()),
                new HttpResponse(550, null),
                new HttpResponse(599, "body".getBytes())
            ]
    }

    def "should throw HttpResponseException in case of Response body is empty"() {
        given:
            def response = new HttpResponse(200, null)

        when:
            httpResponseValidator.checkHttpResponse(response)

        then:
            def e = thrown HttpResponseException
            e.getMessage().contains("Response body is empty")
    }
}
