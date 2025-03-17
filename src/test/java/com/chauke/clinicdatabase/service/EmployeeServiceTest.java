package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.dto.EmployeeDTO;
import com.chauke.clinicdatabase.dto.RegisterRequest;
import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.enums.Roles;
import com.chauke.clinicdatabase.mapper.EmployeeDTOMapper;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private EmployeeService employeeService;
    @Mock private EmployeeRepository employeeRepository;
    private final EmployeeDTOMapper employeeDTOMapper = new EmployeeDTOMapper();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock private AuthService service;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository,service,
                employeeDTOMapper,passwordEncoder);
    }


    @Test
    void getAllEmployeesTest() {
        Collection<EmployeeDTO> employees = employeeService.getEmployees();
        verify(employeeRepository).findAll();
        assertThat(employees).isNotNull();
    }

    @Test
    void getEmployeeByEmailTest() {
        Employee employee = new Employee("Paul", "Doe",
                "paul@email.com", passwordEncoder.encode("pass123"), Roles.GENERAL);

        when(employeeRepository.findEmployeeByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        EmployeeDTO expected = employeeDTOMapper.apply(employee);
        EmployeeDTO actual = employeeService.getEmployeeByEmail(employee.getEmail());

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getNonExistentEmployeeTest() {
        String email = "nonexistent@email.com";
        assertThrows(ResponseStatusException.class,() ->
                employeeService.getEmployeeByEmail(email));

        String message = assertThrows(ResponseStatusException.class,() ->
                employeeService.getEmployeeByEmail(email)).getMessage();

        assertThat(message).isEqualTo("404 NOT_FOUND \"Employee Not Found\"");
    }

    @Test
    void addEmployeeTest() {
        RegisterRequest registerEmployee = RegisterRequest.builder().email("john@gmail.com")
                .firstName("john").lastName("doe").password(passwordEncoder.encode("pass123"))
                .build();
        employeeService.addEmployee(registerEmployee);
        ArgumentCaptor<RegisterRequest> captor = ArgumentCaptor.forClass(RegisterRequest.class);
        verify(service).createEmployee(captor.capture());
    }

    @Test
    void deleteEmployeeTest() {
        Employee employee = new Employee("Paul", "Doe",
                "paul@email.com", passwordEncoder.encode("pass123"), Roles.GENERAL);

        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);
        employeeService.deleteEmployee(employee.getEmail());
        verify(employeeRepository, times(1))
                .removeByEmail(eq(employee.getEmail()));
    }

    @Test
    void deleteNonExistentEmployeeTest() {
        String email = "nonexistent@email.com";
        String message = assertThrows(ResponseStatusException.class,() ->
                employeeService.deleteEmployee(email)).getMessage();

        assertThat(message).isEqualTo("404 NOT_FOUND \"Employee Not Found\"");
    }

    @Test
    void updateEmployeeTest() {
        Employee existingEmployee = new Employee("john", "doe", "john@email.com",
                passwordEncoder.encode("pass123"), Roles.GENERAL);

        RegisterRequest updateEmployeeRequest = RegisterRequest.builder().email("john@email.com")
                .firstName(null).lastName("smith").password(passwordEncoder.encode("pass123"))
                .build();

        when(employeeRepository.findEmployeeByEmail(updateEmployeeRequest.getEmail())).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        Employee updatedEmployee = employeeService.updateEmployee(updateEmployeeRequest);

        assertThat("smith").isEqualTo(updatedEmployee.getLastName());
        assertThat("john").isEqualTo(updatedEmployee.getFirstName());
        assertThat("john@email.com").isEqualTo(updatedEmployee.getEmail());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateNonExistentEmployeeTest() {
        RegisterRequest updateEmployee = RegisterRequest.builder().email("john@gmail.com")
                .firstName("john").lastName("doe").password(passwordEncoder.encode("pass123"))
                .build();

        String message = assertThrows(ResponseStatusException.class,() ->
                employeeService.updateEmployee(updateEmployee)).getMessage();

        assertThat(message).isEqualTo("404 NOT_FOUND \"Employee Not Found\"");
    }
}

