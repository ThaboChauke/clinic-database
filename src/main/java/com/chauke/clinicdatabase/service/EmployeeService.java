package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.dto.AuthResponse;
import com.chauke.clinicdatabase.dto.EmployeeDTO;
import com.chauke.clinicdatabase.mapper.EmployeeDTOMapper;
import com.chauke.clinicdatabase.dto.RegisterRequest;
import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthService authService;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public Collection<EmployeeDTO> getEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeDTOMapper)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email)
                .map(employeeDTOMapper)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found")
        );
    }

    public AuthResponse addEmployee(RegisterRequest employee) {
        return authService.createEmployee(employee);
    }

    public AuthResponse addAdmin(RegisterRequest employee) {
        return authService.createAdmin(employee);
    }

    public ResponseEntity<HttpStatus> deleteEmployee(String email) {
        if (!employeeRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found");
        }
        employeeRepository.removeByEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Employee updateEmployee(RegisterRequest employee) {
        if (employee.getEmail() == null || employee.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        Employee existingEmployee = employeeRepository.findEmployeeByEmail(employee.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found"));

        existingEmployee.setFirstName(Optional.ofNullable(employee.getFirstName()).orElse(existingEmployee.getFirstName()));
        existingEmployee.setLastName(Optional.ofNullable(employee.getLastName()).orElse(existingEmployee.getLastName()));
        existingEmployee.setPassword(Optional.ofNullable(passwordEncoder.encode(employee.getPassword())).orElse(existingEmployee.getPassword()));
        existingEmployee.setEmail(Optional.ofNullable(employee.getEmail()).orElse(existingEmployee.getEmail()));
        return employeeRepository.save(existingEmployee);
    }
}
