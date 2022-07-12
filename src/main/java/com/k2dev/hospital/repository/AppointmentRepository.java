package com.k2dev.hospital.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.k2dev.hospital.model.dto.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
	
	List<Appointment> findAllByHospitalHospitalIdAndAppointmentDate(String hospitalId, LocalDate date);
	
	@Query("select a.appointmentAvailable from Appointment a where a.appointmentId=:appointmentId")
	int findByAppointmentId(String appointmentId);
	
	@Query(value ="select * from appointments where hospital_id=:hospitalId AND appointment_date=:appointmentDate"
			+ " AND appointment_time=:appointmentTime", nativeQuery = true)
	Appointment checkAlreadyExistAppointment(@Param("hospitalId") String hospitalId, 
			@Param("appointmentDate") LocalDate appointmentDate, @Param("appointmentTime") 
			LocalTime appointmentTime);

	List<Appointment> findAllByHospitalHospitalId(String hospitalId);


	Appointment findByHospitalHospitalIdAndAppointmentDateAndAppointmentTime(String hospitalId, LocalDate date,
			LocalTime time);
}
