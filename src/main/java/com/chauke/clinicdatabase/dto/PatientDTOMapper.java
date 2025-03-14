package com.chauke.clinicdatabase.dto;

import com.chauke.clinicdatabase.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PatientDTOMapper implements Function<Patient, PatientDTO> {

    @Override
    public PatientDTO apply(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getFullName(),
                patient.getDateOfBirth(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getIdNumber(),
                patient.getGender()
        );
    }
}
