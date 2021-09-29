package com.prokopovich.persondata.domain.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Person {

    @Id
    @Positive
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Required fields Full Name are not filled")
    @Column(name = "full_name")
    private String fullName;

    @NotEmpty(message = "Required fields Phone are not filled")
    @Pattern(regexp = "\\+?[0-9\\-\\(\\)]+", message = "Invalid Phone")
    @Column(name = "phone")
    private String phone;

    @NotEmpty(message = "Required fields Email are not filled")
    @Email(regexp = "[.\\-_a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+", message = "Invalid Email")
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
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