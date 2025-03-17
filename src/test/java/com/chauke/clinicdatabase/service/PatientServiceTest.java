package com.chauke.clinicdatabase.service;

import com.chauke.clinicdatabase.dto.PatientDTO;
import com.chauke.clinicdatabase.dto.PatientFullDTO;
import com.chauke.clinicdatabase.entity.Allergy;
import com.chauke.clinicdatabase.enums.AllergySeverity;
import com.chauke.clinicdatabase.mapper.*;
import com.chauke.clinicdatabase.entity.Patient;
import com.chauke.clinicdatabase.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    private PatientService patientService;
    @Mock private PatientRepository patientRepository;
    private final PatientDTOMapper patientDTOMapper = new PatientDTOMapper();
    @Mock private PatientFullDTOMapper patientFullDTOMapper;

    @BeforeEach
    void setUp() {
        patientService = new PatientService(patientRepository, patientDTOMapper,
                patientFullDTOMapper);
    }

    @Test
    void getAllPatientsTest() {
        patientService.getAll();
        verify(patientRepository).findAll();
    }

    @Test
    void addingPatientTest() {
        PatientDTO patientDTO = new PatientDTO("John Doe",LocalDate.of(2001,5,21),
              "0123456789",
                "john@gmail.com","24 Nowhere str", "71051623454085", "Male");

        patientService.savePatient(patientDTO);

        ArgumentCaptor<Patient> patientArgumentCaptor = ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository).save(patientArgumentCaptor.capture());
        Patient capturedPatient = patientArgumentCaptor.getValue();
        assertNotNull(capturedPatient);
        assertEquals(capturedPatient.getIdNumber(),patientDTO.getIdNumber());
    }

    @Test
    void addingDuplicatePatientTest() {
        PatientDTO patientDTO = new PatientDTO("John Doe",LocalDate.of(2001,5,21),
                "0123456789",
                "john@gmail.com.","24 Nowhere str", "71051623454085", "Male");

        given(patientRepository.existsByIdNumber(patientDTO.getIdNumber())).willReturn(true);

        assertThatThrownBy(() -> patientService.savePatient(patientDTO))
                .isInstanceOf(ResponseStatusException.class).
                hasMessageContaining("Patient already exists");

        verify(patientRepository, never()).save(any());
    }

    @Test
    void getPatientByIdNumberTest() {
        Patient patient = new Patient("Jane Doe",LocalDate.of(2001,5,21),
                "0123456789", "jane@gmail.com",
                "23 Nowhere str", "7105162345084", "Female");

        when(patientRepository.findPatientByIdNumber("7105162345084")).thenReturn(Optional.of(patient));

        PatientDTO expected = patientDTOMapper.apply(patient);
        PatientDTO actual = patientService.getPatientByIdNumber("7105162345084");

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getNonExistingPatientByIdNumberTest() {
        String idNumber = "71051623454084";
        assertThrows(ResponseStatusException.class, () -> patientService.getPatientByIdNumber(idNumber));

        String message = assertThrows(ResponseStatusException.class,
                () -> patientService.getPatientByIdNumber(idNumber)).getMessage();

        assertEquals("404 NOT_FOUND \"Patient Not Found\"", message);
    }

    @Test
    void getPatientWithNullIdNumberTest() {
        assertThrows(ResponseStatusException.class, () -> patientService.getPatientByIdNumber(null));

        String message = assertThrows(ResponseStatusException.class,
                () -> patientService.getPatientByIdNumber(null)).getMessage();

        assertEquals("400 BAD_REQUEST \"ID number is required\"", message);
    }

    @Test
    void deletePatientByIdNumberTest() {
        Patient patient = new Patient("Jane Doe",LocalDate.of(2001,5,21),
                "0123456789", "jane@gmail.com",
                "23 Nowhere str", "7105162345084", "Female");

        when(patientRepository.existsByIdNumber(patient.getIdNumber()))
                .thenReturn(true);

        patientService.deletePatient(patient.getIdNumber());
        verify(patientRepository, times(1))
                .removePatientByIdNumber(eq(patient.getIdNumber()));
    }

    @Test
    void deleteNonExistentPatientTest() {
        String idNumber = "0304494990490";
        String message = assertThrows(ResponseStatusException.class,
                () -> patientService.deletePatient(idNumber)).getMessage();
        assertEquals("404 NOT_FOUND \"Patient Not Found\"", message);
    }

    @Test
    void updatePatientTest() {
        Patient existingPatient = new Patient("Jane Doe", LocalDate.of(2001, 5, 21),
                "0123456789", "jane@gmail.com",
                "23 Nowhere str", "7105162345084", "Female");

        PatientDTO updatedPatientDTO = new PatientDTO("Jane Smith", null,
                "0987654321", "janesmith@gmail.com",
                null, "7105162345084", "Female");

        when(patientRepository.findPatientByIdNumber(existingPatient.getIdNumber()))
                .thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        Patient updatedPatient = patientService.updatePatient(updatedPatientDTO);

        assertEquals("Jane Smith", updatedPatient.getFullName());
        assertEquals("0987654321", updatedPatient.getPhoneNumber());
        assertEquals("janesmith@gmail.com", updatedPatient.getEmail());
        assertEquals("23 Nowhere str", updatedPatient.getAddress());

        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void updateNonExistentPatientTest() {
        PatientDTO patient = new PatientDTO("Jane Doe",LocalDate.of(2001,5,21),
                "0123456789", "jane@gmail.com",
                "23 Nowhere str", "7105162345084", "Female");

        String message = assertThrows(ResponseStatusException.class,
                () -> patientService.updatePatient(patient)).getMessage();
        assertEquals("404 NOT_FOUND \"Patient Not Found\"", message);
    }

    @Test
    void addAllergyToPatientTest() {
        Patient patient = new Patient("Jane Doe", LocalDate.of(2001, 5, 21),
                "0123456789", "jane@gmail.com",
                "23 Nowhere str", "7105162345084", "Female");
        patient.setAllergies(new ArrayList<>());
        patient.setConditions(new ArrayList<>());
        patient.setImmunizations(new ArrayList<>());
        patient.setTreatments(new ArrayList<>());

        Allergy allergy = new Allergy("Peanuts", AllergySeverity.Severe);

        when(patientRepository.findPatientByIdNumber(patient.getIdNumber())).thenReturn(Optional.of(patient));

        PatientFullDTO patientFullDTO = new PatientFullDTO();
        when(patientFullDTOMapper.apply(patient)).thenReturn(patientFullDTO);

        PatientFullDTO result = patientService.addAllergyToPatient(patient.getIdNumber(), allergy);

        assertThat(patientFullDTO).isEqualTo(result);
        assertThat(patient).isEqualTo(allergy.getPatient());
        assertEquals(patient, allergy.getPatient());

        verify(patientRepository, times(1)).findPatientByIdNumber(patient.getIdNumber());
        verify(patientFullDTOMapper, times(1)).apply(patient);
    }
}
