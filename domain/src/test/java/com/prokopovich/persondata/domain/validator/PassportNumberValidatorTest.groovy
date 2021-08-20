package com.prokopovich.persondata.domain.validator

import spock.lang.Specification

class PassportNumberValidatorTest extends Specification {

    def passportNumberValidator = new PassportNumberValidator()

    def "should return true in case of valid passport number"() {
        expect:
            passportNumberValidator.isValid(passportNumber)

        where:
            passportNumber << [
                "12 34 567890",  // Russia
                "123456789",     // Russia, Ukraine, USA
                "HB1234567",     // Belarus, Latvia, Armenia
                "UH123456",      // Ukraine, Lithuania
                "12345678",      // Lithuania
                "1234567",       // Latvia, Kazakhstan
                "E1234567",      // Estonia, Kazakhstan
                "123456",        // Israel
                "K123456"        // Indonesia
            ]
    }

    def "should return false in case of invalid passport number"() {
        expect:
            !passportNumberValidator.isValid(passportNumber)

        where:
            passportNumber << [
                "123",
                "12345678912345",
                "ABCDEFG",
                "abcdefg",
                "ab123456",
                "123456AS",
                "123DF456",
                "FD12!124",
                "11 22 33 44 A",
                "HB1234567 "
            ]
    }
}
