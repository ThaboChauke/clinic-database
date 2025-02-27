package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Collection<Patient> getAll() {
        return patientRepository.findAll();
    }

    public ResponseEntity<Patient> savePatient(Patient patient) {
        Patient savePatient = patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savePatient);
    }

    public Patient getPatientByIdNumber(String idNumber) {
        return patientRepository.findPatientByIdNumber(idNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found")
        );
    }

    public ResponseEntity<Patient> updatePatient(Patient patient) {
        Patient updatePatient = patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.OK).body(updatePatient);
    }

    @Transactional
    public ResponseEntity<HttpStatus> deletePatient(String idNumber) {
        if (!patientRepository.existsByIdNumber(idNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }
        patientRepository.removePatientByIdNumber(idNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
