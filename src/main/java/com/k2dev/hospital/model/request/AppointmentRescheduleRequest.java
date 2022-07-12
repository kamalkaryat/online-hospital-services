package com.k2dev.hospital.model.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AppointmentRescheduleRequest {
	private String bookingId;
	private String appointmentId;
	private String patientId;
}
