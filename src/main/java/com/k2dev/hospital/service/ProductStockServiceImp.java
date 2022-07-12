package com.k2dev.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.dto.ProductQuantity;
import com.k2dev.hospital.repository.ProductRepository;
import com.k2dev.hospital.repository.ProductStockRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductStockServiceImp implements ProductStockService{
	private final ProductStockRepository productStockrepository;
	private final ProductRepository productRepository;
	
	@Override
	public List<ProductQuantity> findProductsStockInHospital(String hospitalId) {
		return productStockrepository.findAllByHospitalHospitalId(hospitalId);
	}

	@Override
	public ProductQuantity findProductStockInHospital(String hospitalId, String productId) {
		return productStockrepository.findByHospitalHospitalIdAndProductProductId(hospitalId, productId);
	}

	@Override
	public List<ProductQuantity> findProductStocks(String category, String name) {
		Product p= productRepository.findByProductCategoryAndProductName(category, name);
		if(p==null)
			throw new ObjectNotFoundException("No product exists for category"+category+", & name: "+name);
		return productStockrepository.findByProductProductId(p.getProductId());
	}

}
