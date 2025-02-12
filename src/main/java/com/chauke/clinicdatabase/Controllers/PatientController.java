package com.chauke.clinicdatabase.Controllers;

import com.chauke.clinicdatabase.Models.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PatientController {

    private Map<String, Patient> patientMap = new HashMap<>() {{
        put("1", new Patient("1", "John Doe", "02/05/1998",
                "+27846504164" ,"johndoe@gmail.com", "123 sing street"));
     }};

    private List<Patient> patients = List.of(new Patient("1", "John Doe", "02/05/1998",
                                        "+27846504164" ,"johndoe@gmail.com", "123 sing street"));

    @GetMapping("/")
    public String Hello() {
        return "Hello";
    }

    @GetMapping("/api/patients")
    public Collection<Patient> getPatients() {
        return patientMap.values();
    }

    @GetMapping("/api/patients/{id}")
    public Patient getPatients(@PathVariable String id) {
        Patient patient = patientMap.get(id);

        if (patient == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return patient;
    }
}
