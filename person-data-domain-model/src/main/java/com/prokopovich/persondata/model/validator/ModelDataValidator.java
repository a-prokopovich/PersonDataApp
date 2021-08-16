package com.prokopovich.persondata.model.validator;

import com.prokopovich.persondata.model.entity.PassportData;
import com.prokopovich.persondata.model.entity.Person;
import com.prokopovich.persondata.model.exception.InvalidDataException;

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
