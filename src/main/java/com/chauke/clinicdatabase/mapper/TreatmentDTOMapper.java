package com.chauke.clinicdatabase.mapper;

import com.chauke.clinicdatabase.dto.TreatmentDTO;
import com.chauke.clinicdatabase.entity.Treatment;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TreatmentDTOMapper implements Function<Treatment, TreatmentDTO> {
    @Override
    public TreatmentDTO apply(Treatment treatment) {
        return new TreatmentDTO(
                treatment.getTreatment(),
                treatment.getTreatmentType(),
                treatment.getTreatmentDate(),
                treatment.getPrescribedBy()
        );
    }
}
