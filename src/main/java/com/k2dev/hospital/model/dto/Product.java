package com.k2dev.hospital.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.k2dev.hospital.util.Constants.INVALID_PRODUCT_CATEGORY;
import static com.k2dev.hospital.util.Constants.INVALID_PRODUCT_NAME;
import static com.k2dev.hospital.util.Constants.INVALID_PRODUCT_DESCRIPTION;

@Entity
@Table(name= "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
	@Id
	private String productId;
	
	@Column(nullable = false)
	@NotBlank(message = INVALID_PRODUCT_NAME)
	private String productName;
	
	@NotBlank(message = INVALID_PRODUCT_CATEGORY)
	private String productCategory;
	
	@Size(max = 100, message = INVALID_PRODUCT_DESCRIPTION)
	private String productDesc;
}
