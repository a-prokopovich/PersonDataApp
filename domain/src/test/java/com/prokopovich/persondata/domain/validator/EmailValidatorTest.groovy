package com.prokopovich.persondata.validator

import com.prokopovich.persondata.domain.validator.EmailValidator
import spock.lang.Specification

class EmailValidatorTest extends Specification {

    def emailValidator = new EmailValidator()

    def "should return true in case of valid email"() {
        expect:
            emailValidator.isValid(email)

        where:
            email << [
                null,
                "",
                "valid@email.com",
                "123valid@email.com",
                "va1id@emai1.com",
                "va1id_12@emai1.com",
                "va1id-12@emai1.com",
                "v-a-1-i-d-_12@emai17.com",
                "v.a.1.i.d@emai188.com"
            ]
    }

    def "should return false in case of invalid email"() {
        expect:
            !emailValidator.isValid(email)

        where:
            email << [
                "invalid email",
                "invalid@email",
                "invalid.email",
                "invalid@email.com.net",
                "invalid!!!@email.net",
                "invalid@email.123",
                "invalid!!!@email.com",
                "invalid/@email.com",
                "invalid@email..com"
            ]
    }
}
