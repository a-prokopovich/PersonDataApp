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
public class PassportData implements Cloneable {

    @JsonProperty(required = true)
    private int personId;

    @JsonProperty(required = true)
    private String passportNumber;

    @JsonProperty(required = true)
    private String identificationNumber;


    @Override
    public PassportData clone() {
        try {
            return (PassportData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}