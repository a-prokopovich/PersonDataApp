package com.prokopovich.persondata.domain.validator.person

import com.prokopovich.persondata.domain.model.PassportData
import com.prokopovich.persondata.domain.model.Person
import spock.lang.Specification

class RequiredFieldValidatorTest extends Specification {

    def requiredFieldValidator = new RequiredFieldValidator()

    def "should return true in case of Person contains unfilled fields"() {
        expect:
            requiredFieldValidator.containsUnfilledFields(person)

        where:
            person << [
                new Person(0, "Ivan Ivanov", "80335873405", "alex@gmail.com", null),
                new Person(1, null, "80335873405", "alex@gmail.com", null),
                new Person(1, "", "80335873405", "alex@gmail.com", null),
                new Person(0, null, "80335873405", "alex@gmail.com", null),
                new Person(0, "", "80335873405", "alex@gmail.com", null)
            ]
    }

    def "should return false in case of Person not contains unfilled fields"() {
        expect:
            !requiredFieldValidator.containsUnfilledFields(person)

        where:
            person << [
                new Person(1, "Ivan Ivanov", "80335873405", "alex@gmail.com", null),
                new Person(1, "Ivan Ivanov", "", "alex@gmail.com", null),
                new Person(1, "Ivan Ivanov", "80335873405", "", null),
                new Person(1, "Ivan Ivanov", "", "", null),
                new Person(1, "Ivan Ivanov", null, null, null)
            ]
    }

    def "should return true in case of PassportData contains unfilled fields"() {
        expect:
            requiredFieldValidator.containsUnfilledFields(passportData)

        where:
            passportData << [
                new PassportData(0, "HB3452345", "7548564A001PB5"),
                new PassportData(1, null, "7548564A001PB5"),
                new PassportData(1, "", "7548564A001PB5"),
                new PassportData(1, "HB3452345", null),
                new PassportData(1, "HB3452345", ""),
                new PassportData(0, null, "7548564A001PB5"),
                new PassportData(0, "", "7548564A001PB5"),
                new PassportData(0, "HB3452345", null),
                new PassportData(0, "HB3452345", ""),
                new PassportData(1, "", ""),
                new PassportData(1, null, null),
                new PassportData(1, "", null),
                new PassportData(1, null, ""),
                new PassportData(0, null, null),
                new PassportData(0, "", ""),
                new PassportData(0, "", null),
                new PassportData(0, null, "")
            ]
    }

    def "should return false in case of PassportData not contains unfilled fields"() {
        given:
            def passportData = new PassportData(1, "HB3452345", "7548564A001PB5")

        when:
            !requiredFieldValidator.containsUnfilledFields(passportData)

        then:
            true
    }
}