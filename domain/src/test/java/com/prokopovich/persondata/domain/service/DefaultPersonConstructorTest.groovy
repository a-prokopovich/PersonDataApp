package com.prokopovich.persondata.service

import com.prokopovich.persondata.domain.exception.InvalidDataException
import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.domain.service.DefaultPersonConstructor
import com.prokopovich.persondata.domain.exception.PersonConstructorException;
import com.prokopovich.persondata.domain.validator.PersonValidator
import com.prokopovich.persondata.parser.api.PersonParser
import com.prokopovich.persondata.parser.exception.ParserException
import spock.lang.Specification

class DefaultPersonConstructorTest extends Specification {

    def parser = Mock(PersonParser)
    def personValidator = Mock(PersonValidator)

    def personConstructor = new DefaultPersonConstructor(parser, personValidator)

    def "should return new person in case of valid data"() {
        given:
            def inData = new ByteArrayInputStream("valid data".getBytes())
            def person = new Person()

            1 * parser.parse(inData) >> person
            1 * personValidator.validate(person)

        when:
            personConstructor.construct(inData)

        then:
            person
            notThrown(PersonConstructorException)
    }

    def "should throw PersonConstructorException in case of catching ParserException"() {
        given:
            def inData = new ByteArrayInputStream("invalid data".getBytes())

            1 * parser.parse(inData) >> {
                throw new ParserException("parsing error")
            }

            def errorMsg = "Unable to construct Person, reason: parsing error"

        when:
            personConstructor.construct(inData)

        then:
            def e = thrown PersonConstructorException
            e.getMessage() == errorMsg
            e.getCause().getClass() == ParserException
    }

    def "should throw PersonConstructorException in case of catching InvalidDataException"() {
        given:
            def inData = new ByteArrayInputStream("invalid data".getBytes())
            def person = new Person()

            1 * parser.parse(inData) >> person
            1 * personValidator.validate(person) >> {
                throw new InvalidDataException("invalid data")
            }

            def errorMsg = "Unable to construct Person, reason: invalid data"

        when:
            personConstructor.construct(inData)

        then:
            def e = thrown PersonConstructorException
            e.getMessage() == errorMsg
            e.getCause().getClass() == InvalidDataException
    }
}
