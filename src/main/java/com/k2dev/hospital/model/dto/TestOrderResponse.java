package com.k2dev.hospital.model.dto;

import java.time.LocalDateTime;

import com.k2dev.hospital.util.TestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestOrderResponse {
	private String orderId;
	private String patientId;
	private LocalDateTime orderDateTime;
	private String labId;
	private TestStatus status;
}
