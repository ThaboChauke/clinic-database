package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/api/patients")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public Collection<Patient> getPatients() {
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable String id) {
        return patientService.getPatientByIdNumber(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable String id) {
        return patientService.deletePatient(id);
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody @Valid Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@RequestBody @Valid Patient patient) {
        return patientService.updatePatient(patient);
    }

}
