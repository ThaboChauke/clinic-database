package com.chauke.clinicdatabase.repository;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.enums.Roles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EmployeeRepositoryTest {

    @Autowired private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        Employee student = new Employee("John", "Doe",
                "john@gmail.com", "pass123", Roles.GENERAL);
        employeeRepository.save(student);
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void findEmployeeByEmailTest() {
        String email = "john@gmail.com";
        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(email);
        assertThat(email).isEqualTo(employee.get().getEmail());
    }

    @Test
    void existsByEmailTest() {
        String email = "john@gmail.com";
        Boolean exists = employeeRepository.existsByEmail(email);
        assertThat(exists).isTrue();
    }

    @Test
    void removeByEmail() {
        String email = "john@gmail.com";
        Boolean exists = employeeRepository.existsByEmail(email);
        assertThat(exists).isTrue();

        employeeRepository.removeByEmail(email);
        assertThat(employeeRepository.existsByEmail(email)).isFalse();
    }
}