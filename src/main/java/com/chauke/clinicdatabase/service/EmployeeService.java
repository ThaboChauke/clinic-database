package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Collection<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found")
        );
    }

    public ResponseEntity<Employee> addEmployee(Employee employee) {
        Employee employee1 = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee1);
    }

    public ResponseEntity<HttpStatus> deleteEmployee(String email) {
        if (!employeeRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found");
        }
        employeeRepository.removeByEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Employee> updateEmployee(Employee employee) {
        Employee employee1 = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.OK).body(employee1);
    }
}
