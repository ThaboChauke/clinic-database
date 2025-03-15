package com.chauke.clinicdatabase.entity;

import com.chauke.clinicdatabase.enums.ChronicStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Conditions")
@Table(name = "conditions")
@Data
@NoArgsConstructor
public class Conditions {

    @Id
    @SequenceGenerator(name = "chronic_sequence", sequenceName = "condition_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "condition_sequence")
    @Column(name = "id", updatable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "condition", nullable = false, columnDefinition = "TEXT")
    private String condition;

    @Column(name = "diagnosis_date", nullable = false)
    private LocalDate diagnosisDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChronicStatus status;


    public Conditions(Patient patient, String condition, LocalDate diagnosisDate, ChronicStatus status) {
        this.patient = patient;
        this.condition = condition;
        this.diagnosisDate = diagnosisDate;
        this.status = status;
    }
}
