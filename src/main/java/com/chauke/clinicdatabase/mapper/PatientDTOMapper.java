package com.chauke.clinicdatabase.mapper;

import com.chauke.clinicdatabase.dto.PatientDTO;
import com.chauke.clinicdatabase.entity.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class PatientDTOMapper implements Function<Patient, PatientDTO> {

    @Override
    public PatientDTO apply(Patient patient) {
        return new PatientDTO(
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
