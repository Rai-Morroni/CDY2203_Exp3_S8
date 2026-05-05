package com.duoc.backend;

import com.duoc.backend.model.Appointment;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentTest {

    @Test
    void testGettersAndSetters() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        appointment.setPatientId(10);
        appointment.setDate(LocalDate.now());
        appointment.setTime(LocalTime.now());
        appointment.setReason("Control");
        appointment.setVeterinarian("Dr. Perez");

        assertEquals(1, appointment.getId());
        assertEquals(10, appointment.getPatientId());
        assertEquals("Control", appointment.getReason());
        assertEquals("Dr. Perez", appointment.getVeterinarian());
    }

    @Test
    void testConstructor() {
        Appointment appointment = new Appointment(10, LocalDate.now(), LocalTime.now(), "Vacuna", "Dra. Gomez");
        assertEquals(10, appointment.getPatientId());
        assertEquals("Vacuna", appointment.getReason());
    }
}