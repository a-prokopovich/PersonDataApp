package com.prokopovich.persondata.model;

import java.io.Serializable;
import java.util.Objects;

public class PassportData implements Serializable, Cloneable {
    private int personId;
    private String passportNumber;
    private String identificationNumber;

    public PassportData() { }

    public PassportData(int personId, String passportNumber, String identificationNumber) {
        this.personId = personId;
        this.passportNumber = passportNumber;
        this.identificationNumber = identificationNumber;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassportData that = (PassportData) o;
        return personId == that.personId &&
                Objects.equals(passportNumber, that.passportNumber) &&
                Objects.equals(identificationNumber, that.identificationNumber);
    }

    @Override
    public int hashCode() {
        int result = personId;
        result = 31 * result + (passportNumber != null ? passportNumber.hashCode() : 0);
        result = 31 * result + (identificationNumber != null ? identificationNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PassportData:" +
                " personId = " + personId +
                ", passportNumber = " + passportNumber +
                ", identificationNumber = " + identificationNumber + ';';
    }

    @Override
    public PassportData clone() {
        try {
            return (PassportData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
