package com.duoc.backend;

import com.duoc.backend.model.Invoice;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InvoiceTest {

    @Test
    void testGettersAndSetters() {
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setAppointmentId(10);
        invoice.setIssueDate(LocalDate.now());
        invoice.setVatRate(new BigDecimal("0.19"));
        invoice.setSubtotal(new BigDecimal("1000"));
        invoice.setVatAmount(new BigDecimal("190"));
        invoice.setTotal(new BigDecimal("1190"));
        invoice.setNotes("Test");
        invoice.setItems(new ArrayList<>());

        assertEquals(1, invoice.getId());
        assertEquals(10, invoice.getAppointmentId());
        assertNotNull(invoice.getIssueDate());
        assertEquals(new BigDecimal("0.19"), invoice.getVatRate());
        assertEquals(new BigDecimal("1000"), invoice.getSubtotal());
        assertEquals(new BigDecimal("190"), invoice.getVatAmount());
        assertEquals(new BigDecimal("1190"), invoice.getTotal());
        assertEquals("Test", invoice.getNotes());
        assertEquals(0, invoice.getItems().size());
    }
}