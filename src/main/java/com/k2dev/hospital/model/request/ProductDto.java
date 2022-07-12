package com.k2dev.hospital.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {
	private String hospitalId;
	private String productId;
	private double cost;
	private double quantity;
}
