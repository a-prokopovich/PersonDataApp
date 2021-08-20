package com.prokopovich.persondata.service.validator

import spock.lang.Specification

class DefaultInUrlValidatorTest extends Specification {

    def enteredUrlValidator = new DefaultInUrlValidator()

    def "should return true in case of valid url"() {

        expect:
            enteredUrlValidator.checkEnteredUrl(url)

        where:
            url << [
                "https://prokopovich-example.free.beeceptor.com/person/1",
                "http://prokopovich-example.free.beeceptor.com/person/1"
            ]
    }

    def "should return false in case of invalid url"() {

        expect:
            !enteredUrlValidator.checkEnteredUrl(url)

        where:
            url << [
                    "not url",
                    "://example.free./person/1",
                    "https://example",
                    "file://example.free.com/person/1"
            ]
    }
}
