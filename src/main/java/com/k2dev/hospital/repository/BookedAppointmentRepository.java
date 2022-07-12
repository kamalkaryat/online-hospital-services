package com.k2dev.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k2dev.hospital.model.dto.BookedAppointment;

public interface BookedAppointmentRepository extends JpaRepository<BookedAppointment, String> {
	List<BookedAppointment> findAllByPatientPatientId(String patientId);
}

