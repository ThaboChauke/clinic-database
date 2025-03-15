package com.chauke.clinicdatabase.mapper;

import com.chauke.clinicdatabase.dto.PatientFullDTO;
import com.chauke.clinicdatabase.entity.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientFullDTOMapper implements Function<Patient, PatientFullDTO> {

    private final AllergyDTOMapper allergyDTOMapper;
    private final ConditionDTOMapper conditionDTOMapper;
    private final ImmunizationDTOMapper immunizationDTOMapper;
    private final TreatmentDTOMapper treatmentDTOMapper;

    @Override
    public PatientFullDTO apply(Patient patient) {
        return new PatientFullDTO(
                patient.getFullName(),
                patient.getDateOfBirth(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getIdNumber(),
                patient.getGender(),
                patient.getAllergies().stream()
                        .map(allergyDTOMapper)
                        .collect(Collectors.toList()),
                patient.getConditions().stream()
                        .map(conditionDTOMapper)
                        .collect(Collectors.toList()),
                patient.getImmunizations().stream()
                        .map(immunizationDTOMapper)
                        .collect(Collectors.toList()),
                patient.getTreatments().stream()
                        .map(treatmentDTOMapper)
                        .collect(Collectors.toList())
        );
    }
}
