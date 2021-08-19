package com.prokopovich.persondata.service.validator

import spock.lang.Specification

class DefaultEnteredUrlValidatorTest extends Specification {

    def enteredUrlValidator = new DefaultInUrlValidator()

    def "should return true in case of valid url"() {
        given:
            def url = "https://prokopovich-example.free.beeceptor.com/person/1"

        when:
            enteredUrlValidator.checkEnteredUrl(url)

        then:
            true
    }

    def "should return false in case of invalid url"() {

        expect:
            !enteredUrlValidator.checkEnteredUrl(url)

        where:
            url << [
                    "not url",
                    "://example.free./person/1",
                    "https://example",
            ]
    }
}
