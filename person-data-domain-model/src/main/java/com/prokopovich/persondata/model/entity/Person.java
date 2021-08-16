package com.prokopovich.persondata.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Person {

    @JsonProperty(required = true)
    private int id;

    @JsonProperty(required = true)
    private String fullName;

    private String phone;

    private String email;

    private PassportData passportData;
}