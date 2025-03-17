package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.dto.PatientDTO;
import com.chauke.clinicdatabase.dto.PatientFullDTO;
import com.chauke.clinicdatabase.entity.*;
import com.chauke.clinicdatabase.mapper.PatientDTOMapper;
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
    private final PatientDTOMapper patientDTOMapper;

    @GetMapping
    public Collection<PatientDTO> getPatients() {
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getPatientByIdNumber(id));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<PatientFullDTO> getPatientWithDetails(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getPatientWithDetails(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable String id) {
        return patientService.deletePatient(id);
    }

    @PostMapping
    public ResponseEntity<PatientDTO> addPatient(@RequestBody @Valid PatientDTO patientDTO) {
        Patient savedPatient = patientService.savePatient(patientDTO);
        PatientDTO savedPatientDTO = patientDTOMapper.apply(savedPatient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatientDTO);
    }

    @PutMapping
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody @Valid PatientDTO patientDTO) {
        Patient updatedPatient = patientService.updatePatient(patientDTO);
        PatientDTO updatedPatientDTO = patientDTOMapper.apply(updatedPatient);
        return ResponseEntity.ok(updatedPatientDTO);
    }

    @PostMapping("/{idNumber}/allergies")
    public ResponseEntity<PatientFullDTO> addAllergy(@PathVariable String idNumber, @RequestBody Allergy allergy) {
        return ResponseEntity.ok(patientService.addAllergyToPatient(idNumber, allergy));
    }

    @PostMapping("/{idNumber}/condition")
    public ResponseEntity<PatientFullDTO> addCondition(@PathVariable String idNumber, @RequestBody Conditions condition) {
        return ResponseEntity.ok(patientService.addConditionToPatient(idNumber, condition));
    }

    @PostMapping("/{idNumber}/immunization")
    public ResponseEntity<PatientFullDTO> addImmunization(@PathVariable String idNumber, @RequestBody Immunization immunization) {
        return ResponseEntity.ok(patientService.addImmunizationsToPatient(idNumber, immunization));
    }

    @PostMapping("/{idNumber}/treatment")
    public ResponseEntity<PatientFullDTO> addTreatment(@PathVariable String idNumber, @RequestBody Treatment treatment) {
        return ResponseEntity.ok(patientService.addTreatmentToPatient(idNumber, treatment));
    }
}
