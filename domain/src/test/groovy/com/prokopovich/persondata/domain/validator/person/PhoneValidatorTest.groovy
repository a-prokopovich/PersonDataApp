package com.prokopovich.persondata.domain.validator.person

import spock.lang.Specification

class PhoneValidatorTest extends Specification {

    def phoneValidator = new PhoneValidator()

    def "should return true in case of valid phone"() {
        expect:
            phoneValidator.isValid(phone)

        where:
            phone << [
                null,
                "",
                "80291231234",
                "+375-33-631-12-12",
                "(37533)1234567",
                "+37533-123-45-67",
                "+375291234567",
                "217-53-78",
                "(8017)245-54-55"
            ]
    }

    def "should return false in case of invalid phone"() {
        expect:
            !phoneValidator.isValid(phone)

        where:
            phone << [
                "invalid phone",
                "=3759987",
                "+375 33 632 55 11",
                "+375 33 632 55 aa",
                "6352658(Velcome)"
            ]
    }
}
