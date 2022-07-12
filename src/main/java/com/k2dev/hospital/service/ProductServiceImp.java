package com.k2dev.hospital.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.InsufficientObjectsException;
import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Patient;
import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.dto.ProductPurchased;
import com.k2dev.hospital.model.dto.ProductQuantity;
import com.k2dev.hospital.model.request.PurchaseProductRequest;
import com.k2dev.hospital.model.response.PurchasedProductResponse;
import com.k2dev.hospital.repository.ProductStockRepository;
import com.k2dev.hospital.repository.PurchasedProductRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.k2dev.hospital.repository.HospitalRepository;
import com.k2dev.hospital.repository.PatientRepository;
import com.k2dev.hospital.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService{

	private final ProductStockRepository productStockRepository;
	private final ProductRepository productRepository;
	private final PurchasedProductRepository purchasedProductRepository;
	private final PatientRepository patientRepository;
	private final HospitalRepository hospitalRepository;
		
	@Override
	public Product findProduct(String id) {
		return productRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Product not found"));
	}

	
	@Override
	public boolean addNewProduct(Product product) {
		return productRepository.save(product)!=null;
	}

	@Override
	public String purchaseProduct(PurchaseProductRequest request) throws Exception {
		//find product
		Product product= productRepository.findById(request.getProductId())
				.orElseThrow(()-> new ObjectNotFoundException("Product Not found with id: "+request.getProductId()));
		
		//find patient
		Patient patient= patientRepository.findById(request.getPatientId())
				.orElseThrow(()-> new ObjectNotFoundException("Patient Not found with id: "+request.getPatientId()));
		
		//find hospital
		Hospital hospital= hospitalRepository.findByHospitalName(request.getHospitalName());
		
		if(hospital==null)
			throw new ObjectNotFoundException("Hospital Not found with name: "+request.getHospitalName());
		
		ProductQuantity productQuantity= 
				productStockRepository.findByHospitalHospitalIdAndProductProductId(hospital.getHospitalId(), product.getProductId());
		
		//check for requested product quantity
		double avl= productQuantity.getQuantity();
		double req= request.getQuantity();
		
		if(req>avl)			//cannot satisfy the patient request
			throw new InsufficientObjectsException("Products Aren't Sufficient");
		
		productQuantity.setQuantity(avl-req);
		ProductQuantity updatedProductQuantity= productStockRepository.save(productQuantity);
		
		if(updatedProductQuantity==null)			//exception arises when updating the product quantity
			throw new Exception();
		
		String txnId= UUID.randomUUID().toString();
		ProductPurchased productPurchased=ProductPurchased.builder()
				.txnId(txnId)
				.product(product)
				.patient(patient)
				.quantity(request.getQuantity())
				.totalCost(request.getCost()*request.getQuantity())
				.purchaseDateTime(LocalDateTime.now())
				.build();
		
		return purchasedProductRepository.save(productPurchased)!=null ? txnId : null;
	}

	@Override
	public List<ProductPurchased> findProductPurchased(String patientId) {
		return purchasedProductRepository.findAllByPatientPatientId(patientId);		
	}

	@Override
	public List<Product> findProducts() {
		return productRepository.findAll();
	}
	
	@Override
	public List<String> findProductCategory() {
		return productRepository.distinctProduct();
	}

	@Override
	public List<String> findProductNames(String category){
		return productRepository.findProductsName(category);
	}
}
