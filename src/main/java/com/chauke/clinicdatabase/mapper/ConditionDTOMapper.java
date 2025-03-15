package com.chauke.clinicdatabase.mapper;

import com.chauke.clinicdatabase.dto.ConditionDTO;
import com.chauke.clinicdatabase.entity.Conditions;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ConditionDTOMapper implements Function<Conditions, ConditionDTO> {
    @Override
    public ConditionDTO apply(Conditions condition) {
        return new ConditionDTO(
            condition.getCondition(),
            condition.getDiagnosisDate(),
            condition.getStatus().name()
        );
    }
}
