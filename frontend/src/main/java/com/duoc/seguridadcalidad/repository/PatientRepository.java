package com.duoc.seguridadcalidad.repository;

import org.springframework.stereotype.Component;

import com.duoc.seguridadcalidad.model.Patient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PatientRepository {

    private final List<Patient> patients = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(1);

    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            patient.setId(sequence.getAndIncrement());
        }
        patients.add(patient);
        return patient;
    }
}
