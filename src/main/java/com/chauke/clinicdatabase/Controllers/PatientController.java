package com.chauke.clinicdatabase.Controllers;

import com.chauke.clinicdatabase.Models.Patient;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class PatientController {

    private Map<String, Patient> patientMap = new HashMap<>() {{
        put("1", new Patient("John Doe", "02/05/1998",
                "+27846504164" ,"johndoe@gmail.com", "123 sing street", "01020202200",
                "Male"));
     }};


    @GetMapping("/")
    public String Hello() {
        return "Hello";
    }

    @GetMapping("/api/patients")
    public Collection<Patient> getPatients() {
        return patientMap.values();
    }

    @GetMapping("/api/patients/{id}")
    public Patient getPatients(@PathVariable @Valid String id) {
        Patient patient = patientMap.get(id);
        if (patient == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return patient;
    }

    @DeleteMapping("/api/patients/{id}")
    public void deletePatient(@PathVariable String id) {
        Patient patient = patientMap.remove(id);
        if (patient == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

//    @PostMapping("/api/patients")
//    public Patient addPatient(@RequestPart("data") MultipartFile file) {
//        patient.setId(Long.valueOf(UUID.randomUUID().toString()));
//        patientMap.put(String.valueOf(patient.getId()), patient);
//        return patient;
//    }
}
