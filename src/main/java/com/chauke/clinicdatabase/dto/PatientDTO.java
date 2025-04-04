package com.chauke.clinicdatabase.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private String fullName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    @Email private String email;
    private String address;
    private String idNumber;
    private String gender;
}

