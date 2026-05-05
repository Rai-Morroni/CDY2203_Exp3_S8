package com.duoc.backend;

import com.duoc.backend.model.Invoice;
import com.duoc.backend.model.InvoiceLineItem;
import com.duoc.backend.model.InvoiceLineItemType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InvoiceLineItemTest {

    @Test
    void testGettersAndSetters() {
        InvoiceLineItem item = new InvoiceLineItem();
        Invoice invoice = new Invoice();
        item.setId(1);
        item.setInvoice(invoice);
        item.setType(InvoiceLineItemType.SERVICE); // Asegúrate de tener este Enum creado
        item.setDescription("Consulta");
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("5000"));
        item.setLineTotal(new BigDecimal("10000"));

        assertEquals(1, item.getId());
        assertNotNull(item.getInvoice());
        assertEquals("Consulta", item.getDescription());
        assertEquals(2, item.getQuantity());
        assertEquals(new BigDecimal("5000"), item.getUnitPrice());
    }

    @Test
    void testRecalculateLineTotal() {
        InvoiceLineItem item = new InvoiceLineItem(null, "Test", 3, new BigDecimal("1000"));
        item.recalculateLineTotal();
        assertEquals(new BigDecimal("3000"), item.getLineTotal());
    }
}