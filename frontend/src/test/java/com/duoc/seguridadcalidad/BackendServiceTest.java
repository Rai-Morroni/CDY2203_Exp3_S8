package com.duoc.seguridadcalidad;

import com.duoc.seguridadcalidad.dto.AuthRequest;
import com.duoc.seguridadcalidad.dto.AuthResponse;
import com.duoc.seguridadcalidad.service.BackendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class BackendServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private BackendService backendService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inicializamos inyectando manualmente el baseUrl simulado
        backendService = new BackendService(restTemplate, "http://localhost:8080");
    }

    // --- Tests para login ---
    @Test
    void testLoginSuccessWithBearer() {
        AuthRequest request = new AuthRequest();
        request.setUsername("admin");
        request.setPassword("1234");
        
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Bearer token123", HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(AuthRequest.class), eq(String.class)))
                .thenReturn(mockResponse);

        AuthResponse response = backendService.login(request);
        assertEquals("token123", response.getToken());
    }

    @Test
    void testLoginSuccessWithoutBearer() {
        AuthRequest request = new AuthRequest();
        ResponseEntity<String> mockResponse = new ResponseEntity<>("token123", HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(AuthRequest.class), eq(String.class)))
                .thenReturn(mockResponse);

        AuthResponse response = backendService.login(request);
        assertEquals("token123", response.getToken());
    }

    @Test
    void testLoginException() {
        when(restTemplate.postForEntity(anyString(), any(AuthRequest.class), eq(String.class)))
                .thenThrow(new HttpStatusCodeException(HttpStatus.UNAUTHORIZED) {});

        assertThrows(HttpStatusCodeException.class, () -> {
            backendService.login(new AuthRequest());
        });
    }

    // --- Tests para getPatients ---
    @Test
    void testGetPatientsSuccess() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map[] mockArray = new Map[]{map1, map2};
        ResponseEntity<Map[]> mockResponse = new ResponseEntity<>(mockArray, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map[].class)))
                .thenReturn(mockResponse);

        List<Map<String, Object>> result = backendService.getPatients("token");
        assertEquals(2, result.size());
    }

    @Test
    void testGetPatientsNullBody() {
        ResponseEntity<Map[]> mockResponse = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map[].class)))
                .thenReturn(mockResponse);

        List<Map<String, Object>> result = backendService.getPatients("token");
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPatientsException() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Map[].class)))
                .thenThrow(new HttpStatusCodeException(HttpStatus.INTERNAL_SERVER_ERROR) {});

        assertThrows(HttpStatusCodeException.class, () -> {
            backendService.getPatients("token");
        });
    }

    // --- Tests para createPatient ---
    @Test
    void testCreatePatientSuccess() {
        Map<String, Object> patientData = new HashMap<>();
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(patientData);

        Map<String, Object> result = backendService.createPatient("token", patientData);
        assertNotNull(result);
    }
    
    // --- Tests para searchPets (Simulando la construcción de la URL) ---
    @Test
    void testSearchPetsSuccess() {
        Map[] mockArray = new Map[]{new HashMap<>()};
        ResponseEntity<Map[]> mockResponse = new ResponseEntity<>(mockArray, HttpStatus.OK);
        
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(mockResponse);

        List<Map<String, Object>> result = backendService.searchPets("Perro", "Macho", "Norte", 5, "available");
        assertEquals(1, result.size());
    }

    @Test
    void testSearchPetsException() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenThrow(new HttpStatusCodeException(HttpStatus.BAD_REQUEST) {});

        assertThrows(HttpStatusCodeException.class, () -> {
            backendService.searchPets(null, null, null, null, null);
        });
    }

    // --- Tests para pets  ---
    @Test
    void testGetPetsSuccess() {
        ResponseEntity<Map[]> mockResponse = new ResponseEntity<>(new Map[]{new HashMap<>()}, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(mockResponse);

        List<Map<String, Object>> result = backendService.getPets();
        assertEquals(1, result.size());
    }

    @Test
    void testGetAvailablePetsSuccess() {
        ResponseEntity<Map[]> mockResponse = new ResponseEntity<>(new Map[]{new HashMap<>()}, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(mockResponse);

        List<Map<String, Object>> result = backendService.getAvailablePets();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdatePetSuccess() {
        Map<String, Object> mockBody = new HashMap<>();
        ResponseEntity<Map> mockResponse = new ResponseEntity<>(mockBody, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(), eq(Map.class)))
                .thenReturn(mockResponse);

        Map<String, Object> result = backendService.updatePet("token", 1, new HashMap<>());
        assertNotNull(result);
    }

    @Test
    void testDeletePetSuccess() {
        ResponseEntity<Map> mockResponse = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(), eq(Map.class)))
                .thenReturn(mockResponse);

        Map<String, Object> result = backendService.deletePet("token", 1);
        assertNotNull(result);
    }

    // --- Tests para Appointments e Invoices ---
    @Test
    void testGetAppointmentsSuccess() {
        ResponseEntity<Map[]> mockResponse = new ResponseEntity<>(new Map[]{new HashMap<>()}, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(mockResponse);

        List<Map<String, Object>> result = backendService.getAppointments("token");
        assertEquals(1, result.size());
    }

    @Test
    void testCreateAppointmentSuccess() {
        when(restTemplate.postForObject(anyString(), any(), eq(Map.class)))
                .thenReturn(new HashMap<>());

        Map<String, Object> result = backendService.createAppointment("token", new HashMap<>());
        assertNotNull(result);
    }

    @Test
    void testGetInvoicesSuccess() {
        ResponseEntity<Map[]> mockResponse = new ResponseEntity<>(new Map[]{new HashMap<>()}, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Map[].class)))
                .thenReturn(mockResponse);

        List<Map<String, Object>> result = backendService.getInvoices("token");
        assertEquals(1, result.size());
    }

    @Test
    void testGetInvoiceByIdSuccess() {
        ResponseEntity<Map> mockResponse = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Map.class)))
                .thenReturn(mockResponse);

        Map<String, Object> result = backendService.getInvoiceById("token", 1L);
        assertNotNull(result);
    }

    @Test
    void testGetInvoiceByAppointmentIdSuccess() {
        ResponseEntity<Map> mockResponse = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(Map.class)))
                .thenReturn(mockResponse);

        Map<String, Object> result = backendService.getInvoiceByAppointmentId("token", 1L);
        assertNotNull(result);
    }

    @Test
    void testCreateInvoiceSuccess() {
        when(restTemplate.postForObject(anyString(), any(), eq(Map.class)))
                .thenReturn(new HashMap<>());

        Map<String, Object> result = backendService.createInvoice("token", 1L, new HashMap<>());
        assertNotNull(result);
    }
}