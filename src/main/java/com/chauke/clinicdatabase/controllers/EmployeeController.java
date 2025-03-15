package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.dto.AuthResponse;
import com.chauke.clinicdatabase.dto.EmployeeDTO;
import com.chauke.clinicdatabase.mapper.EmployeeDTOMapper;
import com.chauke.clinicdatabase.dto.RegisterRequest;
import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeDTOMapper employeeDTOMapper;

    @GetMapping
    public Collection<EmployeeDTO> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{email}")
    public EmployeeDTO getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @PostMapping
    public ResponseEntity<AuthResponse> addEmployee(@RequestBody RegisterRequest employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployee(employee));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable String email) {
        return employeeService.deleteEmployee(email);
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody RegisterRequest employee) {
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        EmployeeDTO employeeDTO = employeeDTOMapper.apply(updatedEmployee);
        return ResponseEntity.ok(employeeDTO);
    }

    @PostMapping("/admin")
    public ResponseEntity<AuthResponse> addEmployeeAdmin(@RequestBody RegisterRequest employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addAdmin(employee));
    }
}
