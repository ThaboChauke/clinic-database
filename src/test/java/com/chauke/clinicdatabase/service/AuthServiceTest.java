package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.dto.AuthRequest;
import com.chauke.clinicdatabase.dto.AuthResponse;
import com.chauke.clinicdatabase.dto.RegisterRequest;
import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.enums.Roles;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private AuthService authService;
    @Mock private EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authentication;


    @BeforeEach
    void setUp() {
        authService = new AuthService(employeeRepository, passwordEncoder,
                jwtService, authentication);
    }


    @Test
    void createEmployeeTest() {
        RegisterRequest registerEmployee = RegisterRequest.builder().email("john@gmail.com")
                .firstName("john").lastName("doe").password(passwordEncoder.encode("pass123"))
                .build();

        when(employeeRepository.existsByEmail(registerEmployee.getEmail())).thenReturn(false);

        authService.createEmployee(registerEmployee);

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee employee = employeeArgumentCaptor.getValue();
        assertNotNull(employee);
        assertEquals("john@gmail.com", employee.getEmail());
        assertEquals(Roles.GENERAL, employee.getRole());
    }

    @Test
    void createDuplicateEmployeeTest() {
        RegisterRequest registerEmployee = RegisterRequest.builder().email("john@gmail.com")
                .firstName("john").lastName("doe").password(passwordEncoder.encode("pass123"))
                .build();

        when(employeeRepository.existsByEmail(registerEmployee.getEmail())).thenReturn(true);

        assertThrows(ResponseStatusException.class,
                () -> authService.createEmployee(registerEmployee));

        String message = assertThrows(ResponseStatusException.class,
                () -> authService.createEmployee(registerEmployee)).getMessage();

        assertEquals("409 CONFLICT \"Employee already exists\"", message);
    }


    @Test
    void createAdmin() {
        RegisterRequest registerEmployee = RegisterRequest.builder().email("john@gmail.com")
                .firstName("john").lastName("doe").password(passwordEncoder.encode("pass123"))
                .build();

        when(employeeRepository.existsByEmail(registerEmployee.getEmail())).thenReturn(false);

        authService.createAdmin(registerEmployee);

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee employee = employeeArgumentCaptor.getValue();
        assertNotNull(employee);
        assertEquals("john@gmail.com", employee.getEmail());
        assertEquals(Roles.ADMIN, employee.getRole());
    }

    @Test
    void authenticateEmployeeTest() {
        Employee employee = new Employee("john", "doe", "john@email.com",
                passwordEncoder.encode("pass123"), Roles.GENERAL);

        AuthRequest request = AuthRequest.builder().email("john@gmail.com")
                .password(passwordEncoder.encode("pass123")).build();

        when(employeeRepository.findEmployeeByEmail(request.getEmail())).thenReturn(Optional.of(employee));
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        AuthResponse response = authService.authenticate(request);
        verify(jwtService).generateToken(employeeArgumentCaptor.capture());

        assertThat(response).isNotNull();
        assertThat(response).isExactlyInstanceOf(AuthResponse.class);
    }

    @Test
    void authenticateUnregisteredEmployeeTest() {
        AuthRequest request = AuthRequest.builder().email("john@gmail.com")
                .password(passwordEncoder.encode("pass123")).build();

        assertThrows(ResponseStatusException.class, () -> authService.authenticate(request));

        String message = assertThrows(ResponseStatusException.class,
                () -> authService.authenticate(request)).getMessage();

        assertThat(message).isEqualTo("404 NOT_FOUND \"Employee Not Found\"");
    }
}