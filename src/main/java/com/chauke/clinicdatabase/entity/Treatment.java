package com.chauke.clinicdatabase.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Treatment")
@Table(name = "treatment")
@NoArgsConstructor
@Data
public class Treatment {

    @Id
    @SequenceGenerator(name = "treatment_sequence", sequenceName = "treatment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "treatment_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "treatment", nullable = false, columnDefinition = "TEXT")
    private String treatment;

    @Column(name = "treatment_type", nullable = false, columnDefinition = "TEXT")
    private String treatmentType;

    @Column(name = "treatment_date", nullable = false)
    private LocalDate treatmentDate;

    @Column(name = "prescribed_by", nullable = false, columnDefinition = "TEXT")
    private String prescribedBy;

    public Treatment(Patient patient, String treatment, String treatmentType, LocalDate treatmentDate, String prescribedBy) {
        this.patient = patient;
        this.treatment = treatment;
        this.treatmentType = treatmentType;
        this.treatmentDate = treatmentDate;
        this.prescribedBy = prescribedBy;
    }
}
