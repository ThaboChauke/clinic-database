package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.entity.MedicalHistory;
import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/api/patients")
    public Collection<Patient> getPatients() {
        return patientService.getAll();
    }

    @GetMapping("/api/patients/{id}")
    public Patient getPatients(@PathVariable Long id) {
        return patientService.getById(id);
    }

    @DeleteMapping("/api/patients/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable Long id) {
        return patientService.removeById(id);
    }

    @PostMapping("/api/patients")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @GetMapping("/api/patients/id/{idNumber}")
    public Patient getPatient(@PathVariable String idNumber) {
        return patientService.getPatientByIdNumber(idNumber);
    }

//    @GetMapping("/api/patients/medicalhistory/{id}")
//    public ResponseEntity<MedicalHistory> getMedicalHistory(@PathVariable Long id) {
//        return patientService.getMedicalHistory(id);
//    }
}
