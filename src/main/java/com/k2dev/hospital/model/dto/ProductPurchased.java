package com.k2dev.hospital.model.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPurchased {

	@Id
	private String txnId;
	
	@OneToOne
	@JoinColumn(name= "product_id")
	private Product product;
	
	@OneToOne
	@JoinColumn(name= "patient_id")
	private Patient patient;
	
	@Column(nullable = false)
	private double quantity;
	
	@Column(nullable = false)
	private double totalCost;
	
	@Column(nullable = false)
	private LocalDateTime purchaseDateTime;
}
