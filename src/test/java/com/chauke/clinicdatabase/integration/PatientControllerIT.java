package com.chauke.clinicdatabase.integration;

import com.chauke.clinicdatabase.entity.Employee;
import com.chauke.clinicdatabase.enums.Roles;
import com.chauke.clinicdatabase.repository.EmployeeRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PatientControllerIT {

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
        employee.setRole(Roles.GENERAL);
        employee.setPassword(passwordEncoder.encode("pass123"));
        employeeRepository.save(employee);

        jwtToken = loginAndGetToken(employee.getEmail());
        populateDb();
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
        String patientJson = "{"
                + "\"fullName\": \"Jane Doe\","
                + "\"email\": \"jane.doe@example.com\","
                + "\"dateOfBirth\": \"1985-07-20\","
                + "\"gender\": \"female\","
                + "\"idNumber\": \"1234567891012\","
                + "\"phoneNumber\": \"1234567890\""
                + "}";

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isCreated());
    }

    @AfterAll
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testGeneralUserCanAccessPatients() throws Exception {
        mockMvc.perform(get("/api/patients")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testAdminUserCanAccessPatient() throws Exception {
        mockMvc.perform(get("/api/patients")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testCreatePatient() throws Exception {
        String patientJson = "{"
                + "\"fullName\": \"John Doe\","
                + "\"email\": \"john.doe@example.com\","
                + "\"dateOfBirth\": \"1985-07-20\","
                + "\"gender\": \"male\","
                + "\"idNumber\": \"1234567891011\","
                + "\"phoneNumber\": \"1234567891\""
                + "}";

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.idNumber").value("1234567891011"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testCreatePatientWithoutIdNumber() throws Exception {
        String patientJson = "{"
                + "\"fullName\": \"John Doe\","
                + "\"email\": \"john.doe@example.com\","
                + "\"dateOfBirth\": \"1985-07-20\","
                + "\"gender\": \"male\","
                + "\"phoneNumber\": \"1234567891\""
                + "}";

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Id number cannot be null"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testGetPatient() throws Exception {
        mockMvc.perform(get("/api/patients/1234567891012")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Jane Doe"))
                .andExpect(jsonPath("$.idNumber").value("1234567891012"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testGetPatientDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/patients/1234567891019")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient Not Found"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/api/patients/1234567891012")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testDuplicatePatient() throws Exception {
        String patientJson = "{"
                + "\"fullName\": \"Jane Doe\","
                + "\"email\": \"jane.doe@example.com\","
                + "\"dateOfBirth\": \"1985-07-20\","
                + "\"gender\": \"female\","
                + "\"idNumber\": \"1234567891012\","
                + "\"phoneNumber\": \"1234567890\""
                + "}";

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Patient already exists"))
                .andReturn();
    }


    @Test
    @WithMockUser(authorities = "GENERAL")
    void testUpdatePatient() throws Exception {
        String patientJson = "{"
                + "\"fullName\": \"Jane Doe\","
                + "\"address\": \"24 sign street\","
                + "\"idNumber\": \"1234567891012\","
                + "\"phoneNumber\": \"1234567890\""
                + "}";

        mockMvc.perform(put("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Jane Doe"))
                .andExpect(jsonPath("$.idNumber").value("1234567891012"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$.address").value("24 sign street"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testAddingMedicalConditionsToPatient() throws Exception {
        String allergyJson = "{"
                + "\"allergyName\": \"Bee Allergy\","
                + "\"severity\": \"Severe\""
                + "}";

        mockMvc.perform(post("/api/patients/1234567891012/allergies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(allergyJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Jane Doe"))
                .andExpect(jsonPath("$.allergies").isArray())
                .andExpect(jsonPath("$.allergies").hasJsonPath())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "GENERAL")
    void testGetPatientFullDetails() throws Exception {
       mockMvc.perform(get("/api/patients/1234567891012/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Jane Doe"))
                .andExpect(jsonPath("$.idNumber").value("1234567891012"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$.allergies").isArray())
                .andExpect(jsonPath("$.conditions").isArray())
                .andExpect(jsonPath("$.treatments").isArray())
                .andReturn();
    }
}
