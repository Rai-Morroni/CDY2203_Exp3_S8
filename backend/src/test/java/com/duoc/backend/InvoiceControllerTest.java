package com.duoc.backend;

import com.duoc.backend.controller.InvoiceController;
import com.duoc.backend.model.Invoice;
import com.duoc.backend.model.InvoiceLineItem;
import com.duoc.backend.model.InvoiceLineItemType;
import com.duoc.backend.repository.AppointmentRepository;
import com.duoc.backend.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InvoiceControllerTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private InvoiceController invoiceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInvoices() {
        when(invoiceRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<Iterable<Invoice>> response = invoiceController.getAllInvoices();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetInvoiceByIdFound() {
        Invoice inv = new Invoice();
        when(invoiceRepository.findById(1)).thenReturn(Optional.of(inv));
        ResponseEntity<Invoice> response = invoiceController.getInvoiceById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetInvoiceByIdNotFound() {
        when(invoiceRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<Invoice> response = invoiceController.getInvoiceById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void testGetInvoiceByAppointmentNotFoundAppt() {
        when(appointmentRepository.existsById(1)).thenReturn(false);
        ResponseEntity<?> response = invoiceController.getInvoiceByAppointment(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetInvoiceByAppointmentFound() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(List.of(new Invoice()));
        ResponseEntity<?> response = invoiceController.getInvoiceByAppointment(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // --- Pruebas de Ramas: Validaciones de Creación de Factura ---

    @Test
    void testCreateInvoiceAppointmentNotFound() {
        when(appointmentRepository.existsById(1)).thenReturn(false);
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, new Invoice());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceAlreadyExists() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(List.of(new Invoice()));
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, new Invoice());
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceValidationNullPayload() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceValidationEmptyItems() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(new ArrayList<>());
        Invoice inv = new Invoice();
        inv.setItems(new ArrayList<>());
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, inv);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceValidationNegativeVat() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(new ArrayList<>());
        Invoice inv = new Invoice();
        inv.setVatRate(new BigDecimal("-0.1"));
        InvoiceLineItem item = new InvoiceLineItem();
        inv.setItems(List.of(item));
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, inv);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceValidationItemNoType() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(new ArrayList<>());
        Invoice inv = new Invoice();
        InvoiceLineItem item = new InvoiceLineItem();
        inv.setItems(List.of(item));
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, inv);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceValidationItemNoDescription() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(new ArrayList<>());
        Invoice inv = new Invoice();
        InvoiceLineItem item = new InvoiceLineItem();
        item.setType(InvoiceLineItemType.SERVICE); // Asegura que este enum exista
        inv.setItems(List.of(item));
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, inv);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceValidationItemZeroQuantity() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(new ArrayList<>());
        Invoice inv = new Invoice();
        InvoiceLineItem item = new InvoiceLineItem();
        item.setType(InvoiceLineItemType.SERVICE);
        item.setDescription("Consulta");
        item.setQuantity(0); // Falla por cantidad 0
        inv.setItems(List.of(item));
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, inv);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceValidationItemNegativePrice() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(new ArrayList<>());
        Invoice inv = new Invoice();
        InvoiceLineItem item = new InvoiceLineItem();
        item.setType(InvoiceLineItemType.SERVICE);
        item.setDescription("Consulta");
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("-10")); // Falla por precio negativo
        inv.setItems(List.of(item));
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, inv);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateInvoiceSuccess() {
        when(appointmentRepository.existsById(1)).thenReturn(true);
        when(invoiceRepository.findByAppointmentId(1)).thenReturn(new ArrayList<>());
        Invoice inv = new Invoice();
        InvoiceLineItem item = new InvoiceLineItem();
        item.setType(InvoiceLineItemType.SERVICE);
        item.setDescription("Consulta");
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("10000"));
        inv.setItems(List.of(item));
        
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(inv);
        
        ResponseEntity<?> response = invoiceController.createInvoiceForAppointment(1, inv);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}