package com.k2dev.hospital.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseProductRequest {
	private String productId;
	private String patientId;
	private double cost;
	private double quantity;
	private String hospitalName;
	
}
