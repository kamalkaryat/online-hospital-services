package com.k2dev.hospital.service;

import java.util.List;

import com.k2dev.hospital.model.dto.ProductQuantity;

public interface ProductStockService {
	/**
	 * Find stock of every product available in a specific hospital
	 * @param hospitalId id of hospital
	 * @return list of product quantity
	 */
	List<ProductQuantity> findProductsStockInHospital(String hospitalId);
	
	/**
	 * Find stock of a single product which is available in a hospital
	 * @param hospitalId id of the hospital
	 * @param productId id of product
	 * @return product information
	 */
	ProductQuantity findProductStockInHospital(String hospitalId, String productId);
	
	/**
	 * Find stock of a single product which is available in multiple hospitals
	 * @param category product category
	 * @param name product name
	 * @return list of stocks
	 */
	List<ProductQuantity> findProductStocks(String category, String name);
}
