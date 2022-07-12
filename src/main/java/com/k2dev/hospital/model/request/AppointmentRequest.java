package com.k2dev.hospital.model.request;

import java.time.LocalDate;
import java.time.LocalTime;


import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {
	private String appointmentId;
	private String bookingId;
	private Hospital hospital;
	private String patientId;
	private LocalDate date;
	private LocalTime time;
}
