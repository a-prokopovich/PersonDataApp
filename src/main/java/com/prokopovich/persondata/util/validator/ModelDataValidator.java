package com.prokopovich.persondata.util.validator;

import com.prokopovich.persondata.model.PassportData;
import com.prokopovich.persondata.model.Person;
import com.prokopovich.persondata.util.exception.InvalidDataException;

public interface ModelDataValidator {

    /**
     * @throws InvalidDataException if object Person contains invalid data
     * or not all required fields are filled
     */
    void checkPersonData(Person person);

    /**
     * @throws InvalidDataException if object PassportData contains invalid data
     * or not all required fields are filled
     */
    void checkPassportData(PassportData passportData);
}
