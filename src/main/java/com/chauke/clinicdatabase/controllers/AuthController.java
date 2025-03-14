package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.dto.AuthRequest;
import com.chauke.clinicdatabase.dto.AuthResponse;
import com.chauke.clinicdatabase.dto.RegisterRequest;
import com.chauke.clinicdatabase.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.createEmployee(request));
    }

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
