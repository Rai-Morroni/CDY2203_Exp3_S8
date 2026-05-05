package com.duoc.backend;

import com.duoc.backend.controller.PetController;
import com.duoc.backend.model.Pet;
import com.duoc.backend.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PetControllerTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetController petController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPets() {
        when(petRepository.findAll()).thenReturn(Arrays.asList(new Pet(), new Pet()));
        ResponseEntity<Iterable<Pet>> response = petController.getAllPets();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void testGetAllPetsException() {
        when(petRepository.findAll()).thenThrow(new RuntimeException("DB Error"));
        ResponseEntity<Iterable<Pet>> response = petController.getAllPets();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetAvailablePets() {
        when(petRepository.findByStatus("available")).thenReturn(new ArrayList<>());
        ResponseEntity<List<Pet>> response = petController.getAvailablePets();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetPetByIdFound() {
        Pet pet = new Pet();
        pet.setName("Rex");
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));
        ResponseEntity<Pet> response = petController.getPetById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rex", response.getBody().getName());
    }

    @Test
    void testGetPetByIdNotFound() {
        when(petRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<Pet> response = petController.getPetById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreatePetSuccess() {
        Pet pet = new Pet();
        when(petRepository.save(pet)).thenReturn(pet);
        ResponseEntity<?> response = petController.createPet(pet);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testCreatePetException() {
        Pet pet = new Pet();
        when(petRepository.save(pet)).thenThrow(new RuntimeException("DB Error"));
        ResponseEntity<?> response = petController.createPet(pet);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdatePetFound() {
        Pet existingPet = new Pet();
        existingPet.setId(1);
        Pet updatedDetails = new Pet();
        updatedDetails.setName("NewName");
        updatedDetails.setSpecies("NewSpecies");
        updatedDetails.setBreed("NewBreed");
        updatedDetails.setAge(5);
        updatedDetails.setGender("Macho");
        updatedDetails.setLocation("Norte");
        updatedDetails.setPhotos(new ArrayList<>());
        updatedDetails.setStatus("adopted");

        when(petRepository.findById(1)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(any(Pet.class))).thenReturn(existingPet);

        ResponseEntity<?> response = petController.updatePet(1, updatedDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdatePetNotFound() {
        when(petRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<?> response = petController.updatePet(1, new Pet());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeletePetFound() {
        when(petRepository.findById(1)).thenReturn(Optional.of(new Pet()));
        doNothing().when(petRepository).deleteById(1);
        ResponseEntity<?> response = petController.deletePet(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeletePetNotFound() {
        when(petRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<?> response = petController.deletePet(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // --- Pruebas Masivas de Cobertura de Ramas (Search) ---
    @Test
    void testSearchPetsAllCriteria() {
        when(petRepository.findBySpeciesAndGenderAndLocationAndAgeAndStatus(anyString(), anyString(), anyString(), anyInt(), anyString()))
            .thenReturn(new ArrayList<>());
        ResponseEntity<List<Pet>> response = petController.searchPets("Perro", "Macho", "Centro", 3, "available");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSearchPetsSpeciesGenderLocation() {
        when(petRepository.findBySpeciesAndGenderAndLocationAndStatus(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(new ArrayList<>());
        ResponseEntity<List<Pet>> response = petController.searchPets("Perro", "Macho", "Centro", null, "available");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSearchPetsSpeciesGenderAge() {
        when(petRepository.findBySpeciesAndGenderAndAgeAndStatus(anyString(), anyString(), anyInt(), anyString()))
            .thenReturn(new ArrayList<>());
        ResponseEntity<List<Pet>> response = petController.searchPets("Perro", "Macho", null, 3, "available");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void testSearchPetsSpeciesLocationAge() {
        when(petRepository.findBySpeciesAndLocationAndAgeAndStatus(anyString(), anyString(), anyInt(), anyString()))
            .thenReturn(new ArrayList<>());
        ResponseEntity<List<Pet>> response = petController.searchPets("Perro", null, "Centro", 3, "available");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSearchPetsGenderLocationAge() {
        when(petRepository.findByGenderAndLocationAndAgeAndStatus(anyString(), anyString(), anyInt(), anyString()))
            .thenReturn(new ArrayList<>());
        ResponseEntity<List<Pet>> response = petController.searchPets(null, "Macho", "Centro", 3, "available");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSearchPetsSpeciesGender() {
        when(petRepository.findBySpeciesAndGenderAndStatus(anyString(), anyString(), anyString()))
            .thenReturn(new ArrayList<>());
        ResponseEntity<List<Pet>> response = petController.searchPets("Perro", "Macho", null, null, "available");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSearchPetsOnlyStatus() {
        when(petRepository.findByStatus(anyString())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Pet>> response = petController.searchPets(null, null, null, null, "available");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}