package com.k2dev.hospital.model.response;

import javax.persistence.Column;

import com.k2dev.hospital.model.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class PurchasedProductResponse {
	private Product product;
	private double quantity;
	private double totalCost;
}
