package com.chauke.clinicdatabase.controllers;

import com.chauke.clinicdatabase.controllers.auth.AuthRequest;
import com.chauke.clinicdatabase.controllers.auth.AuthResponse;
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

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
