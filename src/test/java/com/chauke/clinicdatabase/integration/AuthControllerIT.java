package com.chauke.clinicdatabase.integration;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.enums.Roles;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthControllerIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeAll
    void setUp() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john@doe.com");
        employee.setRole(Roles.ADMIN);
        employee.setPassword(passwordEncoder.encode("pass123"));
        employeeRepository.save(employee);

    }

    @AfterAll
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void testLogin() throws Exception {
        String email = "john@doe.com";
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\""+ email + "\", \"password\":\"" + "pass123" + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andReturn();
    }

    @Test
    void testUnauthorisedLogin() throws Exception {
        String email = "jake@doe.com";
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\""+ email + "\", \"password\":\"" + "pass123" + "\"}"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void testRegisterEmployee() throws Exception {
        String employeeJson = "{"
                + "\"firstName\": \"Jane\","
                + "\"lastName\": \"Doe\","
                + "\"email\": \"jane.doe@example.com\","
                + "\"password\": \"pass123\""
                + "}";

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andReturn();
    }
}
