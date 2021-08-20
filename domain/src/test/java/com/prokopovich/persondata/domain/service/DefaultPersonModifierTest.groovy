package com.prokopovich.persondata.domain.service

import com.prokopovich.persondata.domain.model.PassportData
import com.prokopovich.persondata.domain.model.Person
import spock.lang.Specification

class DefaultPersonModifierTest extends Specification {

    def personModifier = new DefaultPersonModifier()

    def "should return person with null passportData"() {
        given:
            def passportData = new PassportData(1, "HB3452345","7548564A001PB5")
            def id = 1
            def fullName = "Ivan Ivanov"
            def phone = "80291234567"
            def email = "i@i.net"

            def originalPerson = new Person(id, fullName, phone, email, passportData)
        when:
            def result = personModifier.modify(originalPerson)

        then:
            result == new Person(id, fullName, phone, email, null)
    }
}
