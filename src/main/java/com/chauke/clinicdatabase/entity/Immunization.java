package com.chauke.clinicdatabase.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Immunization")
@Table(name = "immunization")
@NoArgsConstructor
@Data
public class Immunization {

    @Id
    @SequenceGenerator(name = "immunization_sequence", sequenceName = "immunization_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "immunization_sequence")
    @Column(name = "id", updatable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "vaccine", nullable = false, columnDefinition = "TEXT")
    private String vaccine;

    @Column(name = "date_administered", nullable = false)
    private LocalDate dateAdministered;

    public Immunization(String vaccine, Patient patient, LocalDate dateAdministered) {
        this.vaccine = vaccine;
        this.patient = patient;
        this.dateAdministered = dateAdministered;
    }
}
