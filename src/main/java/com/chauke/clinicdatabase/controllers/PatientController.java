package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.dto.PatientDTO;
import com.chauke.clinicdatabase.dto.PatientDTOMapper;
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
    private final PatientDTOMapper patientDTOMapper;

    @GetMapping
    public Collection<PatientDTO> getPatients() {
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getPatientByIdNumber(id));
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

}
