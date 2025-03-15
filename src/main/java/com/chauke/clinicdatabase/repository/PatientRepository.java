package com.chauke.clinicdatabase.repository;

import com.chauke.clinicdatabase.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findPatientByIdNumber(String idNumber);

    boolean existsByIdNumber(String idNumber);

    void removePatientByIdNumber(String idNumber);

    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.allergies" +
            " LEFT JOIN FETCH p.conditions LEFT JOIN FETCH p.immunizations" +
            " LEFT JOIN FETCH p.treatments WHERE p.idNumber = :idNumber")
    Optional<Patient> findPatientWithDetailsByIdNumber(String idNumber);
}
