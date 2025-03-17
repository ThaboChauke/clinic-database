package com.chauke.clinicdatabase.repository;

import com.chauke.clinicdatabase.entity.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PatientRepositoryTest {

    @Autowired private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        Patient george = new Patient("George Fullman", LocalDate.of(2001,5,21),
                "02549494998", "george@email.com",
                "20 sing street", "0304494990490", "Male");
        patientRepository.save(george);
    }

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
    }

    @Test
    void findPatientByIdNumberTest() {
        String idNumber = "0304494990490";
        Optional<Patient> patient = patientRepository.findPatientByIdNumber(idNumber);
        assertThat(patient).isNotNull();
        assertThat(patient.get().getIdNumber()).isEqualTo(idNumber);
    }

    @Test
    void existsByIdNumberTest() {
        String idNumber = "0304494990490";
        boolean exists = patientRepository.existsByIdNumber(idNumber);
        assertThat(exists).isTrue();
    }

    @Test
    void removePatientByIdNumberTest() {
        String idNumber = "0304494990490";
        boolean exists = patientRepository.existsByIdNumber(idNumber);
        assertThat(exists).isTrue();

        patientRepository.removePatientByIdNumber(idNumber);
        assertThat(patientRepository.existsByIdNumber(idNumber)).isFalse();
    }

    @Test
    void findPatientWithDetailsByIdNumberTest() {
        String idNumber = "0304494990490";
        Optional<Patient> patient = patientRepository.findPatientByIdNumber(idNumber);
        assertThat(patient).isNotNull();
        assertThat(patient.get().getIdNumber()).isEqualTo(idNumber);
    }
}