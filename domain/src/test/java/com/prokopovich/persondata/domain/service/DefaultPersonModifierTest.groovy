package com.prokopovich.persondata.service

import com.prokopovich.persondata.domain.model.PassportData
import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.domain.service.DefaultPersonModifier;
import spock.lang.Specification

class DefaultPersonModifierTest extends Specification {

    def personModifier = new DefaultPersonModifier()

    def "should return person with null passportData"() {
        given:
            def person = new Person(1, "Ivan", "100", "i@i.net", new PassportData(1, "HB3452345","7548564A001PB5"))

            def expectedPerson = new Person(1, "Ivan", "100", "i@i.net", null)

        when:
            def result = personModifier.modifyToDisplay(person)

        then:
            result == expectedPerson
    }
}
