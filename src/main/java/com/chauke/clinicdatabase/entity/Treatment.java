//package com.chauke.clinicdatabase.entity;
//
//import jakarta.persistence.*;
//
//@Entity(name = "Treatment")
//@Table(name = "treatment")
//public class Treatment {
//
//    @Id
//    @SequenceGenerator(name = "treatment_sequence", sequenceName = "treatment_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "treatment_sequence")
//    @Column(name = "id", updatable = false)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "patient_id", nullable = false)
//    private Patient patient;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "history_id")
//    private MedicalHistory medicalHistory;
//
//    @Column(name = "treatment", nullable = false, columnDefinition = "TEXT")
//    private String treatment;
//
//    @Column(name = "prescribed_by", nullable = false, columnDefinition = "TEXT")
//    private String prescribedBy;
//
//
//    public Treatment() {}
//
//    public Treatment(Patient patient, String treatment, String prescribedBy) {
//        this.patient = patient;
//        this.treatment = treatment;
//        this.prescribedBy = prescribedBy;
//    }
//}
