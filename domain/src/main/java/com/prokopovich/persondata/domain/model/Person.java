package com.prokopovich.persondata.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Person {

    @Positive
    @JsonProperty(required = true)
    private int id;

    @NotEmpty(message = "Required fields Full Name are not filled")
    @JsonProperty(required = true)
    private String fullName;

    @NotEmpty(message = "Required fields Phone are not filled")
    @Pattern(regexp = "\\+?[0-9\\-\\(\\)]+", message = "Invalid Phone")
    private String phone;

    @NotEmpty(message = "Required fields Email are not filled")
    @Email(regexp = "[.\\-_a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+", message = "Invalid Email")
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