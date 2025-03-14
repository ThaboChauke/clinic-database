package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.dto.PatientDTO;
import com.chauke.clinicdatabase.dto.PatientDTOMapper;
import com.chauke.clinicdatabase.entity.Patient;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }
        patientRepository.removePatientByIdNumber(idNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
