package com.k2dev.hospital.model.response;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppointmentResponse {
	private LocalTime time;
	private int aptAvl;
}
