package com.duoc.backend;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.duoc.backend.controller.PatientController;
import com.duoc.backend.model.Patient;
import com.duoc.backend.repository.PatientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientController patientController;

    public PatientControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPatients() {
        // Arrange
        Patient patient1 = new Patient();
        patient1.setId(1);
        patient1.setName("Firulais");
        patient1.setSpecies("Perro");

        Patient patient2 = new Patient();
        patient2.setId(2);
        patient2.setName("Michi");
        patient2.setSpecies("Gato");

        List<Patient> patients = Arrays.asList(patient1, patient2);
        
        when(patientRepository.findAll()).thenReturn(patients);

        
        ResponseEntity<Iterable<Patient>> response = patientController.getAllPatients();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testGetPatientById() {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1);
        patient.setName("Firulais");
        
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        ResponseEntity<Patient> response = patientController.getPatientById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Firulais", response.getBody().getName());
        verify(patientRepository, times(1)).findById(1);
    }

    @Test
    void testCreatePatient() {
        // Arrange
        Patient patient = new Patient();
        patient.setName("Firulais");

        when(patientRepository.save(patient)).thenReturn(patient);

        ResponseEntity<?> response = patientController.createPatient(patient);

        // Assert - Verifica que devuelva 201 CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Patient result = (Patient) response.getBody();
        assertEquals("Firulais", result.getName());
        verify(patientRepository, times(1)).save(patient);
    }    
    
}