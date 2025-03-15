package com.chauke.clinicdatabase.mapper;

import com.chauke.clinicdatabase.dto.ImmunizationDTO;
import com.chauke.clinicdatabase.entity.Immunization;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ImmunizationDTOMapper implements Function<Immunization, ImmunizationDTO> {
    @Override
    public ImmunizationDTO apply(Immunization immunization) {
        return new ImmunizationDTO(
                immunization.getVaccine(),
                immunization.getDateAdministered()
        );
    }
}
