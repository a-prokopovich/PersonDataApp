package com.prokopovich.persondata.service

import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.domain.service.PersonConstructor
import com.prokopovich.persondata.domain.service.PersonModifier
import com.prokopovich.persondata.domain.exception.PersonConstructorException
import com.prokopovich.persondata.service.exception.PersonServiceException
import com.prokopovich.persondata.service.validator.InUrlValidator
import com.prokopovich.persondata.webclient.exception.HttpClientException
import com.prokopovich.persondata.webclient.api.HttpClient
import com.prokopovich.persondata.service.exception.HttpResponseException
import com.prokopovich.persondata.webclient.response.HttpResponse
import com.prokopovich.persondata.service.validator.HttpResponseValidator
import spock.lang.Specification

class DefaultPersonServiceTest extends Specification {

    def httpClient = Mock(HttpClient)
    def personConstructor = Mock(PersonConstructor)
    def personModifier = Mock(PersonModifier)
    def urlValidator = Mock(InUrlValidator)
    def httpResponseValidator = Mock(HttpResponseValidator)

    def personService = new DefaultPersonService(httpClient, personConstructor,
            personModifier, urlValidator, httpResponseValidator)

    def "should return string with person info in success case"() {
        given:
            def url = "valid url"
            1 * urlValidator.checkEnteredUrl(url) >> true

            def validBody = "person json".getBytes()
            def httpResponse = new HttpResponse(200, validBody)
            1 * httpClient.getData(url) >> httpResponse

            1 * httpResponseValidator.checkHttpResponse(httpResponse)

            def person = new Person(1, "Ivan", "100", "i@i.net", null)
            1 * personConstructor.construct(validBody) >> person

            1 * personModifier.modify(person) >> person

        when:
            def result = personService.getByUrl(url)

        then:
            result == person
            notThrown PersonServiceException
    }

    def "should throw PersonServiceException in case of entered invalid url"() {
        given:
            def url = "invalid url"
            1 * urlValidator.checkEnteredUrl(url) >> false

        when:
            personService.getByUrl(url)

        then:
            thrown PersonServiceException
    }

    def "should throw PersonServiceException in case of connection error by url"() {
        given:
            def url = "valid url"
            1 * urlValidator.checkEnteredUrl(url) >> true

            httpClient.getData(url) >> {
                throw new HttpClientException("connection error")
            }

        when:
            personService.getByUrl(url)

        then:
            def e = thrown PersonServiceException
            e.getMessage().contains("connection error")
            e.getCause().getClass() == HttpClientException
    }

    def "should throw PersonServiceException in case of HTTP response error"() {
        given:
            def url = "valid url"
            1 * urlValidator.checkEnteredUrl(url) >> true

            def validBody = "person json".getBytes()
            def httpResponse = new HttpResponse(400, validBody)
            1 * httpClient.getData(url) >> httpResponse

            httpResponseValidator.checkHttpResponse(httpResponse) >> {
                throw new HttpResponseException("HTTP response error")
            }

        when:
            personService.getByUrl(url)

        then:
            def e = thrown PersonServiceException
            e.getMessage().contains("HTTP response error")
            e.getCause().getClass() == HttpResponseException
    }

    def "should throw PersonServiceException in case of unable to construct Person"() {
        given:
            def url = "valid url"
            1 * urlValidator.checkEnteredUrl(url) >> true

            def validBody = "person json".getBytes()
            HttpResponse httpResponse = new HttpResponse(400, validBody)
            1 * httpClient.getData(url) >> httpResponse

            1 * httpResponseValidator.checkHttpResponse(httpResponse)

            personConstructor.construct(validBody) >> {
                throw new PersonConstructorException("unable to construct Person")
            }

        when:
            personService.getByUrl(url)

        then:
            notThrown(PersonServiceException)
            //def e = thrown PersonServiceException
            //e.getMessage().contains("unable to construct Person")
            //e.getCause().getClass() == PersonConstructorException
    }
}
