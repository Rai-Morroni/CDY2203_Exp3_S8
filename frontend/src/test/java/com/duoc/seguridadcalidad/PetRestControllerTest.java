package com.duoc.seguridadcalidad;

import com.duoc.seguridadcalidad.controller.PetRestController;
import com.duoc.seguridadcalidad.service.BackendService;
import com.duoc.seguridadcalidad.service.JwtCookieService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PetRestControllerTest {

    @Mock
    private BackendService backendService;

    @Mock
    private JwtCookieService jwtCookieService;

    @InjectMocks
    private PetRestController petRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        when(backendService.getPets()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Map<String, Object>>> response = petRestController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAvailable() {
        when(backendService.getAvailablePets()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Map<String, Object>>> response = petRestController.getAvailable();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSearch() {
        when(backendService.searchPets(any(), any(), any(), any(), any())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Map<String, Object>>> response = petRestController.search("Perro", null, null, null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateUnauthorized() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtCookieService.extractToken(request)).thenReturn(null); // Simulamos que no hay token
        ResponseEntity<Map<String, Object>> response = petRestController.create(request, new HashMap<>());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCreateAuthorized() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtCookieService.extractToken(request)).thenReturn("valid-token");
        when(backendService.createPet(anyString(), any())).thenReturn(new HashMap<>());
        ResponseEntity<Map<String, Object>> response = petRestController.create(request, new HashMap<>());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateUnauthorized() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtCookieService.extractToken(request)).thenReturn(null);
        ResponseEntity<Map<String, Object>> response = petRestController.update(1, request, new HashMap<>());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testUpdateAuthorized() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtCookieService.extractToken(request)).thenReturn("valid-token");
        when(backendService.updatePet(anyString(), anyInt(), any())).thenReturn(new HashMap<>());
        ResponseEntity<Map<String, Object>> response = petRestController.update(1, request, new HashMap<>());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteUnauthorized() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtCookieService.extractToken(request)).thenReturn(null);
        ResponseEntity<Map<String, Object>> response = petRestController.delete(1, request);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testDeleteAuthorized() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtCookieService.extractToken(request)).thenReturn("valid-token");
        when(backendService.deletePet(anyString(), anyInt())).thenReturn(new HashMap<>());
        ResponseEntity<Map<String, Object>> response = petRestController.delete(1, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}