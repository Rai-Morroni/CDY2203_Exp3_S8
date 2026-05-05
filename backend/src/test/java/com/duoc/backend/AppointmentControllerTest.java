package com.duoc.backend;

import com.duoc.backend.controller.AppointmentController;
import com.duoc.backend.model.Appointment;
import com.duoc.backend.repository.AppointmentRepository;
import com.duoc.backend.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<Iterable<Appointment>> response = appointmentController.getAllAppointments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAppointmentByIdFound() {
        Appointment appt = new Appointment();
        when(appointmentRepository.findById(1)).thenReturn(Optional.of(appt));
        ResponseEntity<Appointment> response = appointmentController.getAppointmentById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateAppointmentSuccess() {
        Appointment appt = new Appointment();
        appt.setPatientId(1);
        when(patientRepository.existsById(1)).thenReturn(true);
        when(appointmentRepository.save(appt)).thenReturn(appt);
        
        ResponseEntity<?> response = appointmentController.createAppointment(appt);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testDeleteAppointmentFound() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        doNothing().when(appointmentRepository).deleteById(1);
        
        ResponseEntity<String> response = appointmentController.deleteAppointment(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}