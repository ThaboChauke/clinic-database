package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.controllers.auth.AuthRequest;
import com.chauke.clinicdatabase.controllers.auth.AuthResponse;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //    public AuthResponse createEmployee(RegisterRequest registerRequest) {
//        var employee = new Employee(registerRequest.getFirstName(), registerRequest.getLastName(),
//                registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()),
//                Roles.GENERAL);
//
//        employeeRepository.save(employee);
//        var jwtToken = jwtService.generateToken(employee);
//        return AuthResponse.builder().token(jwtToken).build();
//    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var employee = employeeRepository.findEmployeeByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        var jwtToken = jwtService.generateToken(employee);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
