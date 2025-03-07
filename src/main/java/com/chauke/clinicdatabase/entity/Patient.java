package com.chauke.clinicdatabase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Full name cannot be null")
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number", columnDefinition = "TEXT", nullable = false)
    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^(\\+?\\d{1,3})?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$", message = "Invalid phone number")
    private String phoneNumber;

    @Column(name = "email", columnDefinition = "TEXT", unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "id_number", unique = true, nullable = false)
    @NotNull(message = "Id number cannot be null")
    @Size(min = 13, max = 13, message = "ID must be 13 digits")
    @Pattern(regexp = "\\d{13}", message = "ID must contain only digits")
    private String idNumber;

    @Column(name = "gender", columnDefinition = "TEXT", nullable = false)
    @NotNull(message = "Gender cannot be null")
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
