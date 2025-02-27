package com.chauke.clinicdatabase.repository;

import com.chauke.clinicdatabase.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findPatientByIdNumber(String idNumber);

    void deletePatientByIdNumber(String idNumber);

    boolean existsPatientByIdNumber(String idNumber);
}
