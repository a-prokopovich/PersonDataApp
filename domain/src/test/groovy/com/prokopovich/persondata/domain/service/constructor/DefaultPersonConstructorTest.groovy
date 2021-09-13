package com.prokopovich.persondata.domain.service.constructor

import com.prokopovich.persondata.domain.exception.InvalidDataException
import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.domain.exception.PersonConstructorException
import com.prokopovich.persondata.domain.validator.person.PersonValidator
import com.prokopovich.persondata.domain.service.parser.PersonParser
import com.prokopovich.persondata.domain.exception.ParserException
import spock.lang.Specification

class DefaultPersonConstructorTest extends Specification {

    def parser = Mock(PersonParser)
    def personValidator = Mock(PersonValidator)

    def personConstructor = new DefaultPersonConstructor(parser, personValidator)

    def "should return new person in case of valid data"() {
        given:
            def inData = "valid data".getBytes()
            def person = new Person()

            1 * parser.parse(inData) >> person
            1 * personValidator.validate(person)

        when:
            def result = personConstructor.construct(inData)

        then:
            result == person
            notThrown PersonConstructorException
    }

    def "should throw PersonConstructorException in case of exception during parsing"() {
        given:
            def inData = "invalid data".getBytes()

            1 * parser.parse(inData) >> {
                throw new ParserException("parsing error")
            }

        when:
            personConstructor.construct(inData)

        then:
            def e = thrown PersonConstructorException
            e.getMessage().contains("parsing error")
            e.getCause().getClass() == ParserException
    }

    def "should throw PersonConstructorException in case of got invalid data"() {
        given:
            def inData = "invalid data".getBytes()
            def person = new Person()

            1 * parser.parse(inData) >> person
            1 * personValidator.validate(person) >> {
                throw new InvalidDataException("invalid data")
            }

        when:
            personConstructor.construct(inData)

        then:
            def e = thrown PersonConstructorException
            e.getMessage().contains("invalid data")
            e.getCause().getClass() == InvalidDataException
    }
}
