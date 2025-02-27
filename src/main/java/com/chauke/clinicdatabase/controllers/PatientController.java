package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public Collection<Patient> getPatients() {
        return patientService.getAll();
    }

//    @GetMapping("{id}")
//    public Patient getPatients(@PathVariable Integer id) {
//        return patientService.getById(id);
//    }

    @GetMapping("{idNumber}")
    public Patient getPatient(@PathVariable String idNumber) {
        return patientService.getPatientByIdNumber(idNumber);
    }

    @DeleteMapping("{idNumber}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable String idNumber) {
        return patientService.deletePatient(idNumber);
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        return patientService.updatePatient(patient);
    }

}
