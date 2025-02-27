package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<HttpStatus> login() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/logout")
    public ResponseEntity<HttpStatus> logout() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/api/employees")
    public Collection<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/api/employees/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/api/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping("/api/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }

    @PutMapping("/api/employee")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }
}
