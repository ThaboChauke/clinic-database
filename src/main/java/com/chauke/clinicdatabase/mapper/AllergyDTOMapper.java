package com.chauke.clinicdatabase.mapper;

import com.chauke.clinicdatabase.dto.AllergyDTO;
import com.chauke.clinicdatabase.entity.Allergy;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AllergyDTOMapper implements Function<Allergy, AllergyDTO> {

    @Override
    public AllergyDTO apply(Allergy allergy) {
        return new AllergyDTO(
                allergy.getAllergyName(),
                allergy.getSeverity().name()
        );
    }
}
