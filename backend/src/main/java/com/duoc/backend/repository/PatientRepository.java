package com.duoc.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.duoc.backend.model.Patient;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    // Additional query methods can be added here if needed
}