package com.prokopovich.persondata.model.validator

import com.prokopovich.persondata.model.entity.PassportData
import com.prokopovich.persondata.model.exception.InvalidDataException
import spock.lang.Specification

class PassportDataValidatorTest extends Specification {

    def requiredFieldValidator = Mock(RequiredFieldValidator)
    def passportNumberValidator = Mock(PassportNumberValidator)
    //def passportData = Mock(PassportData)

    def passportDataValidator = new PassportDataValidator()

    def "should not throw exception in case of PassportData contains valid data"() {
        given:
        //    def passportData = null
        //def  passportNumber = "v"

            1 * requiredFieldValidator.containsUnfilledFields(passportData) >> false
            1 * passportNumberValidator.isValid(passportData.passportNumber) >> true

        when:
            passportDataValidator.validate(passportData)

        then:
            notThrown InvalidDataException
    }

    def "should throw InvalidDataException in case of not all required fields are filled"() {
        given:
            //    def passportData = null
            //def  passportNumber = "v"

            requiredFieldValidator.containsUnfilledFields(passportData) >> true

        when:
            passportDataValidator.validate(passportData)

        then:
            def e = thrown InvalidDataException
            e.getMessage() == "Invalid data, reason: not all required fields are filled"
    }

    def "should throw InvalidDataException in case of invalid passportNumber"() {
        given:
            def passportData = new PassportData(1, "GT5676567", "7868h9870")

            requiredFieldValidator.containsUnfilledFields(passportData) >> false
            passportNumberValidator.isValid(passportData.passportNumber) >> false

        when:
            passportDataValidator.validate(passportData)

        then:
            def e = thrown InvalidDataException
            e.getMessage() == "Invalid data, reason: invalid field with passportNumber"
    }
}
