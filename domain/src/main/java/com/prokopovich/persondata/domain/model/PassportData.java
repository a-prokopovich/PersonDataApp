package com.prokopovich.persondata.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PassportData {

    @Positive
    @JsonProperty(required = true)
    private int personId;

    @NotEmpty(message = "Required fields Passport Number are not filled")
    @Pattern(regexp = "[A-Z0-9]{2}[\\u0020]?[0-9]{2}[\\u0020]?[0-9]{2,8}", message = "Invalid Passport Number")
    @JsonProperty(required = true)
    private String passportNumber;

    @NotEmpty(message = "Required fields Identification Number are not filled")
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