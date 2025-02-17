package com.chauke.clinicdatabase.Controllers;

import com.chauke.clinicdatabase.Models.Patient;
import com.chauke.clinicdatabase.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class PatientController {

    @Autowired
    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @GetMapping("/")
    public String Hello() {
        return "Hello";
    }

    @GetMapping("/api/patients")
    public Collection<Patient> getPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/api/patients/{id}")
    public Patient getPatients(@PathVariable Long id) {
        return patientRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found")
        );
    }

    @DeleteMapping("/api/patients/{id}")
    public void deletePatient(@PathVariable Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }
        patientRepository.deleteById(id);
    }

    @PostMapping("/api/patients")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Patient savePatient = patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savePatient);
    }
}
