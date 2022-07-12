package com.k2dev.hospital.model.request;

import com.k2dev.hospital.model.dto.Hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductStock {
	private String productId;
	private String productName;
	private String productCategory;
	private Hospital hospital;
	private double cost;
	private double quantity;
}
