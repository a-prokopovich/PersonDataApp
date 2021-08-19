package com.prokopovich.persondata.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
        String result = "Person: " +
            "id = " + id +
            ", fullName = " + fullName +
            ", phone = " + phone +
            ", email = " + email + "; ";
        if (passportData != null) {
            result += passportData.toString();
        }
        return result;
    }
}