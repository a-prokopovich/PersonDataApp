package com.prokopovich.persondata.service

import com.prokopovich.persondata.model.entity.Person
import com.prokopovich.persondata.service.exception.PersonConstructorException
import com.prokopovich.persondata.service.exception.PersonServiceException
import com.prokopovich.persondata.service.api.PersonConstructor
import com.prokopovich.persondata.service.api.PersonModifier
import com.prokopovich.persondata.service.validator.EnteredUrlValidator
import com.prokopovich.persondata.webclient.exception.HttpClientException
import com.prokopovich.persondata.webclient.api.HttpClient
import com.prokopovich.persondata.webclient.exception.HttpResponseException
import com.prokopovich.persondata.webclient.response.HttpResponse
import com.prokopovich.persondata.webclient.validator.HttpResponseValidator
import spock.lang.Specification

class DefaultPersonServiceTest extends Specification {

    def httpClient = Mock(HttpClient)
    def personConstructor = Mock(PersonConstructor)
    def personModifier = Mock(PersonModifier)
    def urlValidator = Mock(EnteredUrlValidator)
    def httpResponseValidator = Mock(HttpResponseValidator)

    def personService = new DefaultPersonService(httpClient, personConstructor,
            personModifier, urlValidator, httpResponseValidator)

    def "should return string with person info in success case"() {
        given:
            def url = "valid url"
            1 * urlValidator.checkEnteredUrl(url) >> true

            def validBody = new ByteArrayInputStream("person json".getBytes())
            HttpResponse httpResponse = new HttpResponse(200, validBody)
            1 * httpClient.getData(url) >> httpResponse

            1 * httpResponseValidator.checkHttpResponse(httpResponse)

            def person = new Person(1, "Ivan", "100", "i@i.net", null)
            1 * personConstructor.construct(validBody) >> person

            1 * personModifier.modifyToDisplay(person) >> person

            def personInfo = person.toString()

        when:
            def result = personService.getDataFromUrl(url)

        then:
            result == personInfo
            notThrown PersonServiceException
    }

    def "should throw PersonServiceException in case of entered invalid url"() {
        given:
            def url = "invalid url"
            1 * urlValidator.checkEnteredUrl(url) >> false

            def errorMsg = "Unable to get Person from URL, reason: entered invalid URL"

        when:
            personService.getDataFromUrl(url)

        then:
            def e = thrown PersonServiceException
            e.getMessage() == errorMsg
    }

    def "should throw PersonServiceException in case of connection error by url"() {
        given:
            def url = "valid url"
            1 * urlValidator.checkEnteredUrl(url) >> true
            httpClient.getData(url) >> {
                throw new HttpClientException("connection error")
            }

            def errorMsg = "Unable to get Person from URL, reason: connection error"

        when:
            personService.getDataFromUrl(url)

        then:
            def e = thrown PersonServiceException
            e.getMessage() == errorMsg
            e.getCause().getClass() == HttpClientException
    }

    def "should throw PersonServiceException in case of HTTP response error"() {
        given:
            def url = "valid url"
            1 * urlValidator.checkEnteredUrl(url) >> true

            def validBody = new ByteArrayInputStream("person json".getBytes())
            HttpResponse httpResponse = new HttpResponse(400, validBody)
            1 * httpClient.getData(url) >> httpResponse

            httpResponseValidator.checkHttpResponse(httpResponse) >> {
                throw new HttpResponseException("HTTP response error")
            }

            def errorMsg = "Unable to get Person from URL, reason: HTTP response error"

        when:
            personService.getDataFromUrl(url)

        then:
            def e = thrown PersonServiceException
            e.getMessage() == errorMsg
            e.getCause().getClass() == HttpResponseException
    }

    def "should throw PersonServiceException in case of unable to construct Person"() {
        given:
            def url = "valid url"
            1 * urlValidator.checkEnteredUrl(url) >> true

            def validBody = new ByteArrayInputStream("person json".getBytes())
            HttpResponse httpResponse = new HttpResponse(400, validBody)
            1 * httpClient.getData(url) >> httpResponse

            1 * httpResponseValidator.checkHttpResponse(httpResponse)

            personConstructor.construct(validBody) >> {
                throw new PersonConstructorException("unable to construct Person")
            }

            def errorMsg = "Unable to get Person from URL, reason: unable to construct Person"

        when:
            personService.getDataFromUrl(url)

        then:
            def e = thrown PersonServiceException
            e.getMessage() == errorMsg
            e.getCause().getClass() == PersonConstructorException
    }
}
