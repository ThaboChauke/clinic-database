package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.dto.PatientDTO;
import com.chauke.clinicdatabase.dto.PatientFullDTO;
import com.chauke.clinicdatabase.entity.*;
import com.chauke.clinicdatabase.mapper.PatientDTOMapper;
import com.chauke.clinicdatabase.mapper.PatientFullDTOMapper;
import com.chauke.clinicdatabase.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientDTOMapper patientDTOMapper;
    private final PatientFullDTOMapper patientFullDTOMapper;

    public Collection<PatientDTO> getAll() {
        return patientRepository.findAll().stream()
                .map(patientDTOMapper).collect(Collectors.toList());
    }

    public Patient savePatient(PatientDTO patientDTO) {
        boolean check = patientRepository.existsByIdNumber(patientDTO.getIdNumber());

        if (check) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Patient already exists");
        }

        Patient patient = new Patient();
        patient.setFullName(patientDTO.getFullName());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setEmail(patientDTO.getEmail());
        patient.setAddress(patientDTO.getAddress());
        patient.setIdNumber(patientDTO.getIdNumber());
        patient.setGender(patientDTO.getGender());

        return patientRepository.save(patient);
    }

    public PatientDTO getPatientByIdNumber(String idNumber) {
        if (idNumber == null || idNumber.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID number is required");
        }

        return patientRepository.findPatientByIdNumber(idNumber).map(patientDTOMapper)
                .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found")
        );
    }

    public Patient updatePatient(PatientDTO patientDTO) {

        if (patientDTO.getIdNumber() == null || patientDTO.getIdNumber().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID number is required");
        }

        Patient existingPatient = patientRepository.findPatientByIdNumber(patientDTO.getIdNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));

        existingPatient.setFullName(Optional.ofNullable(patientDTO.getFullName()).orElse(existingPatient.getFullName()));
        existingPatient.setDateOfBirth(Optional.ofNullable(patientDTO.getDateOfBirth()).orElse(existingPatient.getDateOfBirth()));
        existingPatient.setPhoneNumber(Optional.ofNullable(patientDTO.getPhoneNumber()).orElse(existingPatient.getPhoneNumber()));
        existingPatient.setEmail(Optional.ofNullable(patientDTO.getEmail()).orElse(existingPatient.getEmail()));
        existingPatient.setAddress(Optional.ofNullable(patientDTO.getAddress()).orElse(existingPatient.getAddress()));
        existingPatient.setGender(Optional.ofNullable(patientDTO.getGender()).orElse(existingPatient.getGender()));

        return patientRepository.save(existingPatient);
    }

    @Transactional
    public ResponseEntity<HttpStatus> deletePatient(String idNumber) {
        if (!patientRepository.existsByIdNumber(idNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found");
        }
        patientRepository.removePatientByIdNumber(idNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public PatientFullDTO getPatientWithDetails(String idNumber) {
        return patientRepository.findPatientWithDetailsByIdNumber(idNumber)
                .map(patientFullDTOMapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));
    }

    @Transactional
    public PatientFullDTO addAllergyToPatient(String idNumber, Allergy allergy) {
        Patient patient = patientRepository.findPatientByIdNumber(idNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));

        allergy.setPatient(patient);
        patient.getAllergies().add(allergy);
        return patientFullDTOMapper.apply(patient);
    }

    @Transactional
    public PatientFullDTO addConditionToPatient(String idNumber, Conditions condition) {
        Patient patient = patientRepository.findPatientByIdNumber(idNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));

        condition.setPatient(patient);
        patient.getConditions().add(condition);
        return patientFullDTOMapper.apply(patient);
    }

    @Transactional
    public PatientFullDTO addImmunizationsToPatient(String idNumber, Immunization immunization) {
        Patient patient = patientRepository.findPatientByIdNumber(idNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));

        immunization.setPatient(patient);
        patient.getImmunizations().add(immunization);
        return patientFullDTOMapper.apply(patient);
    }

    @Transactional
    public PatientFullDTO addTreatmentToPatient(String idNumber, Treatment treatment) {
        Patient patient = patientRepository.findPatientByIdNumber(idNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient Not Found"));

        treatment.setPatient(patient);
        patient.getTreatments().add(treatment);
        return patientFullDTOMapper.apply(patient);
    }
}
