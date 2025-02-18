package com.chauke.clinicdatabase.entity;

import com.chauke.clinicdatabase.enums.ChronicStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "ChronicConditions")
@Table(name = "chronic_conditions")
public class ChronicConditions {

    @Id
    @SequenceGenerator(name = "chronic_sequence", sequenceName = "chronic_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chronic_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "condition", nullable = false, columnDefinition = "TEXT")
    private String condition;

    @Column(name = "diagnosis_date", nullable = false)
    private LocalDate diagnosisDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChronicStatus status;

    public ChronicConditions() {}

    public ChronicConditions(Patient patient, String condition, LocalDate diagnosisDate, ChronicStatus status) {
        this.patient = patient;
        this.condition = condition;
        this.diagnosisDate = diagnosisDate;
        this.status = status;
    }
}
