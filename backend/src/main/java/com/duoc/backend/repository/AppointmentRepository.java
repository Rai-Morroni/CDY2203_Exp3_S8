package com.duoc.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.duoc.backend.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(Integer patientId);
}