package com.chauke.clinicdatabase.integration;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.enums.Roles;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import com.jayway.jsonpath.JsonPath;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeControllerIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    private String jwtToken;

    @BeforeAll
    void setUp() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john@doe.com");
        employee.setRole(Roles.ADMIN);
        employee.setPassword(passwordEncoder.encode("pass123"));
        employeeRepository.save(employee);

        jwtToken = loginAndGetToken(employee.getEmail());
        populateDb();
    }

    @AfterAll
    void tearDown() {
        employeeRepository.deleteAll();
    }

    private String loginAndGetToken(String email) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\""+ email + "\", \"password\":\"" + "pass123" + "\"}"))
                .andExpect(status().isOk())
                .andReturn();
        return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
    }

    private void populateDb() throws Exception {
        String employeeJson = "{"
                + "\"firstName\": \"Jane\","
                + "\"lastName\": \"Doe\","
                + "\"email\": \"jane.doe@example.com\","
                + "\"password\": \"pass123\""
                + "}";

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testAdminUserCanAccessEmployees() throws Exception {
        mockMvc.perform(get("/api/employee")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testGeneralUserCanAccessEmployees() throws Exception {
        mockMvc.perform(get("/api/employee")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testCreateEmployee() throws Exception {
        String employeeJson = "{"
                + "\"firstName\": \"Bruce\","
                + "\"lastName\": \"Doe\","
                + "\"email\": \"bruce.doe@example.com\","
                + "\"password\": \"pass123\""
                + "}";

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isString())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testCreateEmployeeWithoutEmail() throws Exception {
        String employeeJson = "{"
                + "\"firstName\": \"Bruce\","
                + "\"lastName\": \"Doe\","
                + "\"password\": \"pass123\""
                + "}";

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("must not be null"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetEmployee() throws Exception {
        String email = "jane.doe@example.com";
        mockMvc.perform(get("/api/employee/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetEmployeeDoesNotExist() throws Exception {
        String email = "kim.doe@example.com";
        mockMvc.perform(get("/api/employee/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee Not Found"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeletePatient() throws Exception {
        String email = "jane.doe@example.com";
        mockMvc.perform(delete("/api/employee/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDuplicateEmployee() throws Exception {
        String employeeJson = "{"
                + "\"firstName\": \"John\","
                + "\"lastName\": \"Doe\","
                + "\"email\": \"john@doe.com\","
                + "\"password\": \"pass123\""
                + "}";

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Employee already exists"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUpdatePatient() throws Exception {
        String employeeJson = "{"
                + "\"firstName\": \"Jane\","
                + "\"lastName\": \"Smith\","
                + "\"email\": \"jane.doe@example.com\","
                + "\"password\": \"pass123\""
                + "}";

        mockMvc.perform(put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
                .andReturn();
    }
}
