package com.prokopovich.persondata.parser.jsonparser

import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.parser.exception.ParserException

import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class JsonPersonParserTest extends Specification {

    def jsonPersonParser = new JsonPersonParser()

    def "should return Person object in success case"() {
        given:
            def validPersonIn = new ByteArrayInputStream("person valid json".getBytes())
            def personText = IOUtils.toString(validPersonIn, StandardCharsets.UTF_8.name())

            def person = new Person()
            new Gson().fromJson(personText, Person.class) >> person

        when:
            jsonPersonParser.parse(validPersonIn)

        then:
            person
            notThrown ParserException
    }

    //def "should throw ParserException in case of parser error"() {
    //    given:
    //        def invalidPersonIn = "person invalid inputStream"
//
    //        new Gson().fromJson(invalidPersonIn, Person.class)
//
    //        def errorMsg = "Unable to parse the InputStream, reason: error of convert InputStream to String"
//
    //    when:
    //        jsonPersonParser.parse(invalidPersonIn)
//
    //    then:
    //        def e = thrown ParserException
    //        e.getMessage() == errorMsg
    //}
}
