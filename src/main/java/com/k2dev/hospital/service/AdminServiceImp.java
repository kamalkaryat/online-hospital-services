package com.k2dev.hospital.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.request.HospitalAdminSignupRequest;
import com.k2dev.hospital.repository.HospitalAdminRepository;
import com.k2dev.hospital.repository.ProductRepository;
import com.k2dev.hospital.util.Converter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImp implements AdminService{

	private final ProductRepository productRepository;
	private final HospitalAdminService haAdminService;

	@Override
	public Product addProduct(Product product) {
		product.setProductId(UUID.randomUUID().toString());
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		System.out.println(product);
		return productRepository.save(product);
	}

	@Override
	public boolean deleteProduct(String id) {
		productRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean addHospitalAdmin(HospitalAdminSignupRequest signupRequest) {
		return haAdminService.saveHospitalAdmin(signupRequest);
	}
	
}
