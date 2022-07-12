package com.k2dev.hospital.service;

import java.util.List;

import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.request.HospitalAdminSignupRequest;

public interface AdminService {
	
	Product addProduct(Product product);
	
	Product updateProduct(Product product);
	
	boolean deleteProduct(String id);

	boolean addHospitalAdmin(HospitalAdminSignupRequest signupRequest);
	
}
