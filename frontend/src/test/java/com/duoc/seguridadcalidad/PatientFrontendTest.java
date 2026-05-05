package com.duoc.seguridadcalidad;

import com.duoc.seguridadcalidad.model.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PatientFrontendTest {

    @Test
    void testGettersAndSetters() {
        Patient p = new Patient();
        p.setId(1L);
        p.setName("Boby");
        p.setSpecies("Perro");
        p.setBreed("Pug");
        p.setAge(5);
        p.setOwner("Juan");

        assertEquals(1L, p.getId());
        assertEquals("Boby", p.getName());
        assertEquals("Perro", p.getSpecies());
        assertEquals("Pug", p.getBreed());
        assertEquals(5, p.getAge());
        assertEquals("Juan", p.getOwner());
    }

    @Test
    void testConstructor() {
        Patient p = new Patient(2L, "Michi", "Gato", "Persa", 3, "Ana");
        assertEquals(2L, p.getId());
        assertEquals("Michi", p.getName());
    }
}