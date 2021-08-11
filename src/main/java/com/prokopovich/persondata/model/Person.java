package com.prokopovich.persondata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Person implements Cloneable {

    @JsonProperty(required = true)
    private int id;

    @JsonProperty(required = true)
    private String fullName;

    private String phone;

    private String email;

    private PassportData passportData;


    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}