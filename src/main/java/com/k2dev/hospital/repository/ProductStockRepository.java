package com.k2dev.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.k2dev.hospital.model.dto.ProductQuantity;

public interface ProductStockRepository extends JpaRepository<ProductQuantity, Long>{

	/**
	 * Find product stock in a specific hospital
	 * @param hospitalId id of hospital
	 * @return list of product stocks
	 */
	List<ProductQuantity> findAllByHospitalHospitalId(String hospitalId);
	
	/**
	 * Find a specific product stock in a specific hospital
	 * @param hospitalId id of hospital
	 * @param productId id of the product
	 * @return product information
	 */
	ProductQuantity findByHospitalHospitalIdAndProductProductId(String hospitalId, String productId);

	/**
	 * Find all product stocks for a specific product
	 * @param id product id
	 * @return list of product stock
	 */
	List<ProductQuantity> findByProductProductId(String id);
}
