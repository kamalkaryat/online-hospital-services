package com.k2dev.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k2dev.hospital.model.dto.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {
	Patient findByLoginUsername(String username);
}
