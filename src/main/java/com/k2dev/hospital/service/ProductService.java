package com.k2dev.hospital.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.dto.ProductPurchased;
import com.k2dev.hospital.model.request.PurchaseProductRequest;

public interface ProductService {
	/**
	 * Insert a new product into database
	 * @param product information
	 * @return flag
	 */
	boolean addNewProduct(Product product);
	
	/**
	 * For finding a particular product using its id
	 * @param id product id
	 * @return Product
	 * @exception NotFoundException if requested product doesn't found
	 */
	Product findProduct(String id);
	
	/**
	 * Find all the products available in database
	 * @return list of products
	 */
	List<Product> findProducts();
	
	/**
	 * Used for purchasing a product
	 * @param request product purchase request
	 * @return string transaction id
	 * @throws NotFoundException if requested product or patient don't found
	 * @throws Exception 
	 */
	String purchaseProduct(PurchaseProductRequest request) throws Exception;

	/**
	 * Used to find all the products which are purchased by a patient
	 * @param patientId Patient Id
	 * @return list of purchased products
	 */
	List<ProductPurchased> findProductPurchased(String patientId);
	
	/**
	 * Used to find distinct product category
	 * @return category list
	 */
	List<String> findProductCategory();
	
	/**
	 * Used to find a list of products which comes under a specific category
	 * @param category category
	 * @return list of product names
	 */
	List<String> findProductNames(String category);
}
