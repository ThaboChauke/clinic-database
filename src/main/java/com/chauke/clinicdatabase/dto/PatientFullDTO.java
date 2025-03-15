package com.chauke.clinicdatabase.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientFullDTO {
    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    @Email private String email;
    private String address;
    private String idNumber;
    private String gender;
    List<AllergyDTO> allergies;
    List<ConditionDTO> conditions;
    List<ImmunizationDTO> immunizations;
    List<TreatmentDTO> treatments;
}
