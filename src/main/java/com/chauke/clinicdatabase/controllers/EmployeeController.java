package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public Collection<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{email}")
    public Employee getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable String email) {
        return employeeService.deleteEmployee(email);
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }
}
