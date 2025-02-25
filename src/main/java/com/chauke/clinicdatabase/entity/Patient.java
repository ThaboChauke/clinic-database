package com.chauke.clinicdatabase.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity(name = "Patient")
@Table(name = "patient")
public class Patient {

    @Id
    @SequenceGenerator(name = "patient_sequence", sequenceName = "patient_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

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

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private MedicalHistory medicalHistory;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Allergy> allergies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Immunization> immunizations;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Treatment> treatments;

    public Patient() {}

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public List<Immunization> getImmunizations() {
        return immunizations;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", medicalHistory=" + medicalHistory +
                ", allergies=" + allergies.toString() +
                ", immunizations=" + immunizations +
                ", treatments=" + treatments +
                '}';
    }
}
