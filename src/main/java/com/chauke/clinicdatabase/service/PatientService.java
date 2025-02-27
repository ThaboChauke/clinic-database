package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.repository.PatientRepository;
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

//    public Patient getById(Integer id) {
//        return patientRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found")
//        );
//    }

    public Collection<Patient> getAll() {
        return patientRepository.findAll();
    }

//    public ResponseEntity<HttpStatus> removeById(Integer id) {
//        if (!patientRepository.existsById(id)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
//        }
//        patientRepository.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

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

    public ResponseEntity<HttpStatus> deletePatient(String idNumber) {
        if (!patientRepository.existsPatientByIdNumber(idNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }
        patientRepository.deletePatientByIdNumber(idNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
