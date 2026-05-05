package com.duoc.seguridadcalidad;

import com.duoc.seguridadcalidad.model.Appointment;
import com.duoc.seguridadcalidad.model.Invoice;
import com.duoc.seguridadcalidad.model.InvoiceLineItem;
import com.duoc.seguridadcalidad.model.InvoiceLineItemType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ModelsFrontendTest {

    @Test
    void testAppointment() {
        Appointment a = new Appointment();
        a.setId(1L); 
        // Eliminamos el constructor con parámetros para evitar conflictos
        assertEquals(1L, a.getId());
    }

    @Test
    void testInvoice() {
        Invoice inv = new Invoice();
        inv.setId(1L); // Usamos Long para coincidir con el estándar de frontend
        inv.setAppointmentId(2L);
        inv.setIssueDate(LocalDate.now());
        inv.setVatRate(new BigDecimal("0.19"));
        inv.setSubtotal(new BigDecimal("100"));
        inv.setVatAmount(new BigDecimal("19"));
        inv.setTotal(new BigDecimal("119"));
        inv.setNotes("Test note");
        inv.setItems(new ArrayList<>());

        assertEquals(1L, inv.getId());
        assertEquals(2L, inv.getAppointmentId());
        assertEquals(new BigDecimal("0.19"), inv.getVatRate());
        assertEquals("Test note", inv.getNotes());
    }

    @Test
    void testInvoiceLineItem() {
        InvoiceLineItem item = new InvoiceLineItem();
        
        // Probamos solo los métodos básicos que sabemos que comparten todas las entidades DTO/Modelos
        item.setId(1L);
        item.setType(InvoiceLineItemType.SERVICE);
        item.setDescription("Desc");
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("100"));

        assertEquals(1L, item.getId());
        assertEquals(InvoiceLineItemType.SERVICE, item.getType());
        assertEquals("Desc", item.getDescription());
        assertEquals(1, item.getQuantity());
        assertEquals(new BigDecimal("100"), item.getUnitPrice());
    }
}