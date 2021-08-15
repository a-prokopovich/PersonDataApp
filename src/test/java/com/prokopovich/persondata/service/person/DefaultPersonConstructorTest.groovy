package com.prokopovich.persondata.service.person

import com.prokopovich.persondata.model.Person
import com.prokopovich.persondata.util.exception.PersonConstructorException
import com.prokopovich.persondata.util.exception.InvalidDataException
import com.prokopovich.persondata.util.parser.PersonParser
import com.prokopovich.persondata.util.parser.exception.ParserException
import com.prokopovich.persondata.util.validator.ModelDataValidator
import spock.lang.Specification

class DefaultPersonConstructorTest extends Specification {

    def parser = Mock(PersonParser)
    def dataValidator = Mock(ModelDataValidator)

    def personConstructor = new DefaultPersonConstructor(parser, dataValidator)

    def "should return new person in case of valid data"() {
        given:
            def inData = new ByteArrayInputStream("valid data".getBytes())
            def person = new Person()

            1 * parser.parse(inData) >> person
            1 * dataValidator.checkPersonData(person)

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
    }

    def "should throw PersonConstructorException in case of catching InvalidDataException"() {
        given:
            def inData = new ByteArrayInputStream("invalid data".getBytes())
            def person = new Person()

            1 * parser.parse(inData) >> person
            1 * dataValidator.checkPersonData(person) >> {
                throw new InvalidDataException("invalid data")
            }

            def errorMsg = "Unable to construct Person, reason: invalid data"

        when:
            personConstructor.construct(inData)

        then:
            def e = thrown PersonConstructorException
            e.getMessage() == errorMsg
    }
}