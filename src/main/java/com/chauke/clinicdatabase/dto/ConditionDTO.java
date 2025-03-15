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
public class ConditionDTO {
    private String condition;
    private LocalDate startDate;
    private String status;
}
