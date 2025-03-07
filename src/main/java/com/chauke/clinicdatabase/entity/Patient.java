package com.chauke.clinicdatabase.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Patient")
@Table(name = "patient")
@NoArgsConstructor
@Data
public class Patient {

    @Id
    @SequenceGenerator(name = "patient_sequence", sequenceName = "patient_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence")
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "full_name", nullable = false, columnDefinition = "TEXT")
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "phone_number", columnDefinition = "TEXT")
    private String phoneNumber;

    @Column(name = "email", columnDefinition = "TEXT", unique = true)
    private String email;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "id_number", unique = true, nullable = false)
    private String idNumber;

    @Column(name = "gender", columnDefinition = "TEXT", nullable = false)
    private String gender;

    public Patient(String fullName, LocalDate dateOfBirth,
                   String phoneNumber, String email, String address, String idNumber, String gender) {

        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.idNumber = idNumber;
        this.gender = gender;
    }
}
