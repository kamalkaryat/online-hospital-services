package com.k2dev.hospital.model.request;

import java.time.LocalDateTime;

import com.k2dev.hospital.model.dto.Lab;
import com.k2dev.hospital.model.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TestRequest {
	private String patientId;
	private Product product;
	private LocalDateTime dateTime;
	private Lab lab;
}
