package com.prokopovich.persondata.model;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable, Cloneable {
    private int id;
    private String fullName;
    private String phone;
    private String email;
    private PassportData passportData;

    public Person() { }

    public Person(int id, String fullName, String phone, String email, PassportData passportData) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.passportData = passportData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PassportData getPassportData() {
        return passportData;
    }

    public void setPassportData(PassportData passportData) {
        this.passportData = passportData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(fullName, person.fullName) &&
                Objects.equals(phone, person.phone) &&
                Objects.equals(email, person.email) &&
                Objects.equals(passportData, person.passportData);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (passportData != null ? passportData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String line = "Person: " +
                "id = " + id +
                ", fullName = " + fullName +
                ", phone = " + phone +
                ", email = " + email;
        if (passportData != null) {
            line += ", " + passportData;
        }
        return line;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
