package com.chauke.clinicdatabase.mapper;

import com.chauke.clinicdatabase.dto.EmployeeDTO;
import com.chauke.clinicdatabase.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeDTOMapper implements Function<Employee, EmployeeDTO> {
    @Override
    public EmployeeDTO apply(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }
}
