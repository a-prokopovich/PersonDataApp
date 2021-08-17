package com.prokopovich.persondata.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PassportData {

    @JsonProperty(required = true)
    private int personId;

    @JsonProperty(required = true)
    private String passportNumber;

    @JsonProperty(required = true)
    private String identificationNumber;

    @Override
    public String toString() {
        return "passport data: " +
            "personId = " + personId +
            ", passportNumber = " + passportNumber +
            ", identificationNumber = " + identificationNumber;
    }
}