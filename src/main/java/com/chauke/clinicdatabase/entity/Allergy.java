package com.chauke.clinicdatabase.entity;

import com.chauke.clinicdatabase.enums.AllergySeverity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Allergy")
@Table(name = "allergy")
@NoArgsConstructor
@Data
public class Allergy {

    @Id
    @SequenceGenerator(name = "allergy_sequence", sequenceName = "allergy_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allergy_sequence")
    @Column(name = "id", updatable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "allergy", columnDefinition = "TEXT", nullable = false)
    private String allergyName;

    @Column(name = "severity", nullable = false)
    @Enumerated(EnumType.STRING)
    private AllergySeverity severity;

    public Allergy(Patient patient, String allergyName, AllergySeverity severity) {
        this.patient = patient;
        this.allergyName = allergyName;
        this.severity = severity;
    }
}
