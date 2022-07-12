package com.k2dev.hospital.model.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookedAppointmentResponse {
	private String bookingId;
	private String hospitalName;
	private LocalDate aptDate;
	private LocalTime aptTime;
}
