package com.prokopovich.persondata.validator

import com.prokopovich.persondata.domain.model.PassportData;
import com.prokopovich.persondata.domain.exception.InvalidDataException
import com.prokopovich.persondata.domain.validator.PassportDataValidator
import com.prokopovich.persondata.domain.validator.RequiredFieldValidator
import com.prokopovich.persondata.domain.validator.PassportNumberValidator

import spock.lang.Specification

class PassportDataValidatorTest extends Specification {

    def requiredFieldValidator = Mock(RequiredFieldValidator)
    def passportNumberValidator = Mock(PassportNumberValidator)
    def passportData = Mock(PassportData)

    def passportDataValidator = new PassportDataValidator(passportNumberValidator, requiredFieldValidator)

    def "should not throw exception in case of PassportData contains valid data"() {
        given:
            1 * requiredFieldValidator.containsUnfilledFields(passportData) >> false
            1 * passportNumberValidator.isValid(passportData.passportNumber) >> true

        when:
            passportDataValidator.validate(passportData)

        then:
            notThrown InvalidDataException
    }

    def "should throw InvalidDataException in case of not all required fields are filled"() {
        given:
            requiredFieldValidator.containsUnfilledFields(passportData) >> true

        when:
            passportDataValidator.validate(passportData)

        then:
            def e = thrown InvalidDataException
            e.getMessage() == "Invalid data, reason: not all required fields are filled"
    }

    def "should throw InvalidDataException in case of invalid passport number"() {
        given:
            requiredFieldValidator.containsUnfilledFields(passportData) >> false
            passportNumberValidator.isValid(passportData.passportNumber) >> false

        when:
            passportDataValidator.validate(passportData)

        then:
            def e = thrown InvalidDataException
            e.getMessage() == "Invalid data, reason: invalid field with passportNumber"
    }
}
