package com.chauke.clinicdatabase.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "Immunization")
@Table(name = "immunization")
public class Immunization {

    @Id
    @SequenceGenerator(name = "immunization_sequence", sequenceName = "immunization_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "immunization_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private MedicalHistory medicalHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "vaccine", nullable = false, columnDefinition = "TEXT")
    private String vaccine;

    @Column(name = "date_administered", nullable = false)
    private LocalDate dateAdministered;

    @Column(name = "next_dose")
    private LocalDate nextDose;

    public Immunization() {}

    public Immunization(String vaccine, Patient patient, LocalDate dateAdministered, LocalDate nextDose) {
        this.vaccine = vaccine;
        this.patient = patient;
        this.dateAdministered = dateAdministered;
        this.nextDose = nextDose;
    }
}
