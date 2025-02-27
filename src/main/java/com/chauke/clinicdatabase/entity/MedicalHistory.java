//package com.chauke.clinicdatabase.entity;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity(name = "MedicalHistory")
//@Table(name = "medical_history")
//public class MedicalHistory {
//
//    @Id
//    @SequenceGenerator(name = "history_sequence", sequenceName = "history_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_sequence")
//    @Column(name = "id", updatable = false)
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "patient_id", nullable = false)
//    private Patient patient;
//
//    @Column(name = "visit_date", nullable = false)
//    private LocalDate visitDate;
//
//    @Column(name = "family_history", columnDefinition = "TEXT")
//    private String familyHistory;
//
//    @Column(name = "surgical_history", columnDefinition = "TEXT")
//    private String surgicalHistory;
//
//    @Column(name = "last_updated", nullable = false, updatable = false)
//    private LocalDateTime lastUpdated;
//
//    @OneToMany(mappedBy = "medicalHistory", cascade = CascadeType.ALL)
//    private List<Treatment> treatments;
//
//    @OneToMany(mappedBy = "medicalHistory", cascade = CascadeType.ALL)
//    private List<Immunization> immunizations;
//
//
//    public MedicalHistory() {}
//
//    public MedicalHistory(Patient patient, LocalDate visitDate, String familyHistory, String surgicalHistory) {
//        this.patient = patient;
//        this.visitDate = visitDate;
//        this.familyHistory = familyHistory;
//        this.surgicalHistory = surgicalHistory;
//    }
//
//    public MedicalHistory(String surgicalHistory, String familyHistory, LocalDate visitDate, Patient patient) {
//        this.surgicalHistory = surgicalHistory;
//        this.familyHistory = familyHistory;
//        this.visitDate = visitDate;
//        this.patient = patient;
//    }
//
//    @PreUpdate
//    public void setLastUpdated() {
//        this.lastUpdated = LocalDateTime.now();
//    }
//}
