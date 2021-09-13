package com.prokopovich.persondata.domain.validator.person

import com.prokopovich.persondata.domain.model.Person
import com.prokopovich.persondata.domain.exception.InvalidDataException
import spock.lang.Specification

class PersonValidatorTest extends Specification {

    def phoneValidator = Mock(PhoneValidator)
    def emailValidator = Mock(EmailValidator)
    def requiredFieldValidator = Mock(RequiredFieldValidator)
    def passportDataValidator = Mock(PassportDataValidator)

    def personValidator = new PersonValidator(phoneValidator, emailValidator, passportDataValidator, requiredFieldValidator)
    def person = Mock(Person)

    def "should not throw exception in case of Person contains valid data"() {
        given:
            1 * requiredFieldValidator.containsUnfilledFields(person) >> false
            1 * phoneValidator.isValid(person.getPhone()) >> true
            1 * emailValidator.isValid(person.getEmail()) >> true

        when:
            personValidator.validate(person)

        then:
            notThrown InvalidDataException
    }

    def "should throw InvalidDataException in case of not all required fields are filled"() {
        given:
            1 * requiredFieldValidator.containsUnfilledFields(person) >> true

        when:
            personValidator.validate(person)

        then:
            def e = thrown InvalidDataException
            e.getMessage().contains("not all required fields are filled")
    }

    def "should throw InvalidDataException in case of invalid phone"() {
        given:
            1 * requiredFieldValidator.containsUnfilledFields(person) >> false
            1 * phoneValidator.isValid(person.getPhone()) >> false

        when:
            personValidator.validate(person)

        then:
            def e = thrown InvalidDataException
            e.getMessage().contains("invalid field with phone number")
    }

    def "should throw InvalidDataException in case of invalid email"() {
        given:
            1 * requiredFieldValidator.containsUnfilledFields(person) >> false
            1 * phoneValidator.isValid(person.getPhone()) >> true
            1 * emailValidator.isValid(person.getEmail()) >> false

        when:
            personValidator.validate(person)

        then:
            def e = thrown InvalidDataException
            e.getMessage().contains("invalid field with email")
    }
}
