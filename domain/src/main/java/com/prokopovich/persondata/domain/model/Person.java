package com.prokopovich.persondata.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Person {

    @JsonProperty(required = true)
    private int id;

    @JsonProperty(required = true)
    private String fullName;

    private String phone;

    private String email;

    private PassportData passportData;

    @Override
    public String toString() {
        return "Person: " +
            "id = " + id +
            ", fullName = " + fullName +
            ", phone = " + phone +
            ", email = " + email +
            ", " + passportData;
    }
}