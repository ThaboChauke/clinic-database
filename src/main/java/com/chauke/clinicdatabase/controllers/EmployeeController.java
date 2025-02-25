package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<HttpStatus> login() {
        return employeeService.login();
    }

    @GetMapping("/api/logout")
    public ResponseEntity<HttpStatus> logout() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/api/employees")
    public ResponseEntity<HttpStatus> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/api/employees/{id}")
    public ResponseEntity<HttpStatus> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/api/employees")
    public ResponseEntity<HttpStatus> addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee();
    }
}
