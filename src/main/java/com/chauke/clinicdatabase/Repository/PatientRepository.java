package com.chauke.clinicdatabase.Repository;

import com.chauke.clinicdatabase.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
