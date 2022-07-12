package com.k2dev.hospital.model.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppointmentDto {
	private String appointmentId;
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	private int appointmentAvailable;
}
