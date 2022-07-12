package com.k2dev.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k2dev.hospital.model.dto.ProductPurchased;

public interface PurchasedProductRepository extends JpaRepository<ProductPurchased, String> {
	/**
	 * Find the products which are purchased by a particular patient
	 * @param patientId
	 * @return list a list of purchased products
	 */
	List<ProductPurchased> findAllByPatientPatientId(String patientId);	
}
