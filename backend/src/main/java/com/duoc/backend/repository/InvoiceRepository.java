package com.duoc.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.duoc.backend.model.Invoice;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
    List<Invoice> findByAppointmentId(Integer appointmentId);
}
