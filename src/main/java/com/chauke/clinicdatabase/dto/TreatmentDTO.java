package com.chauke.clinicdatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDTO {
    private String treatment;
    private String treatmentType;
    private LocalDate treatmentDate;
    private String prescribedBy;
}
