package com.prokopovich.persondata.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
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