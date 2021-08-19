package com.prokopovich.persondata.parser.jsonparser

import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.parser.exception.ParserException

import com.google.gson.Gson
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class JsonPersonParserTest extends Specification {

    def jsonPersonParser = new JsonPersonParser()

    def "should return Person object in success case"() {
        given:
            def person = new Person(1, "Ivan Ivanov", "80335873405", "alex@gmail.com", null)
            def personJson = new Gson().toJson(person)

            byte[] validPersonIn = personJson.getBytes(StandardCharsets.UTF_8)

        when:
            def result = jsonPersonParser.parse(validPersonIn)

        then:
            result == person
            notThrown ParserException
    }

    def "should throw ParserException in case of parser error"() {
        given:
            def invalidPersonIn = "person invalid json".getBytes(StandardCharsets.UTF_8)

        when:
            jsonPersonParser.parse(invalidPersonIn)

        then:
            thrown ParserException
    }
}
