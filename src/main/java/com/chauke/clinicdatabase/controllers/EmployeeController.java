package com.chauke.clinicdatabase.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @PostMapping("/api/login")
    public ResponseEntity<HttpStatus> login() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
