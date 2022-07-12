package com.k2dev.hospital.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.dto.ProductQuantity;
import com.k2dev.hospital.model.request.ProductDto;
import com.k2dev.hospital.model.request.ProductStock;

public interface HospitalService {
	/**
	 * Find hospitals based on some filters 
	 * @param filters specified by user
	 * @return filtered list of hospitals
	 */
	List<Hospital> findHospitals(String filters);
	
	/**
	 * Find a specific hospital using its id
	 * @param hospitalId id of hospital
	 * @return hospital hospital details
	 * @throws NotFoundException if hospital doesn't exists
	 */
	Hospital findHospital(String hospitalId) throws NotFoundException;
	
	/**
	 * Register a hospital with this platform
	 * @param hospital hospital
	 * @return true on success
	 */
	boolean saveHospital(Hospital hospital);
	
	/**
	 * If a hospital wants to provide the online service for a particular product
	 * then the hospital admin has to register product with this platform.
	 * Stock & price of a product can be also updated.
	 * @param product information
	 * @return true on success
	 */
	boolean addOrUpdateProductInHospital(ProductQuantity product);
	
	/**
	 * If a hospital wants to stop the online service for a product
	 * then the hospital admin has to remove that product from this platform
	 * @param hIds hospital id
	 * @param productId product id
	 * @return true on success
	 */
	boolean removeProductFromHospital(String hId, String productId);
	
	/**
	 * Find the current stock of products in a hospital
	 * @param hId hospital id
	 * @return list of product information
	 */
	List<ProductQuantity> findProductQuantityInHospital(String hId);
	
	/**
	 * Find only active hospitals. Used by users other than admin
	 * @return list of active hospitals
	 */
	List<Hospital> findAllActiveHospitals();

	/**
	 * Either enable or disable a hospital based on the supplied action
	 * @param id hospital id
	 * @param action enable or disable string
	 * @return true on success
	 */
	boolean manageHospital(String id, String action);

	/**
	 * Find all the hospitals. Used by admin user
	 * @return list of hospitals
	 */
	List<Hospital> findAllHospitals();
	
}
