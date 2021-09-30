package com.prokopovich.persondata.domain.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "passport_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PassportData {

    @Id
    @Positive
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Required fields Passport Number are not filled")
    @Pattern(regexp = "[A-Z0-9]{2}[\\u0020]?[0-9]{2}[\\u0020]?[0-9]{2,8}", message = "Invalid Passport Number")
    @Column(name = "passport_number")
    private String passportNumber;

    @NotEmpty(message = "Required fields Identification Number are not filled")
    @Column(name = "identification_number")
    private String identificationNumber;

    //@OneToOne
    //@MapsId
    //@JoinColumn(name = "id")
    //private Person personInfo;

    @Override
    public String toString() {
        return "passport data: " +
            "id = " + id +
            ", passportNumber = " + passportNumber +
            ", identificationNumber = " + identificationNumber;
    }
}