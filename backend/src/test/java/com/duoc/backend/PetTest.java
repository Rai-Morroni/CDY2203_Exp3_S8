package com.duoc.backend;

import com.duoc.backend.model.Pet;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PetTest {

    @Test
    void testGettersAndSetters() {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Max");
        pet.setSpecies("Perro");
        pet.setBreed("Pug");
        pet.setAge(3);
        pet.setGender("Macho");
        pet.setLocation("Centro");
        pet.setStatus("available");
        
        List<String> photos = Arrays.asList("foto1.jpg");
        pet.setPhotos(photos);

        assertEquals(1, pet.getId());
        assertEquals("Max", pet.getName());
        assertEquals("Perro", pet.getSpecies());
        assertEquals("Pug", pet.getBreed());
        assertEquals(3, pet.getAge());
        assertEquals("Macho", pet.getGender());
        assertEquals("Centro", pet.getLocation());
        assertEquals("available", pet.getStatus());
        assertEquals(1, pet.getPhotos().size());
    }

    @Test
    void testConstructor() {
        List<String> photos = Arrays.asList("foto1.jpg");
        Pet pet = new Pet("Luna", "Gato", "Persa", 2, "Hembra", "Sur", photos);
        assertEquals("Luna", pet.getName());
        assertEquals("available", pet.getStatus()); // El constructor setea available por defecto
    }
}