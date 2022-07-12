package com.k2dev.hospital.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.model.dto.Area;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Lab;
import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.dto.ProductQuantity;
import com.k2dev.hospital.model.request.ProductDto;
import com.k2dev.hospital.model.request.ProductStock;
import com.k2dev.hospital.repository.HospitalRepository;
import com.k2dev.hospital.repository.LabRepository;
import com.k2dev.hospital.repository.ProductRepository;
import com.k2dev.hospital.repository.ProductStockRepository;
import com.k2dev.hospital.util.ObjectFinder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HospitalServiceImp implements HospitalService {

	private final HospitalRepository hospitalRepository;
	private final ProductStockRepository productStockRepository;
	private final ProductRepository productRepository;
	private final ObjectFinder objectFinder;

	@Override
	public Hospital findHospital(String id) {
		return hospitalRepository.findById(id).orElse(null);
	}

	@Override
	public List<Hospital> findHospitals(String filters) {
		return hospitalRepository.findAll();
	}

	@Override
	public boolean saveHospital(Hospital hospital) {
		hospital.setHospitalStatus(true);
		hospital.setHospitalId(UUID.randomUUID().toString());
		
		Area area= hospital.getArea();
		if(area==null)
			throw new ObjectNotFoundException("saveHospital: area can't be null");
		
		area.setAreaId(objectFinder.findAreaId(area.getPincode(), area.getAreaName()));
		hospital.setArea(area);
		return hospitalRepository.save(hospital)!=null;
	}

	@Override
	public boolean addOrUpdateProductInHospital(ProductQuantity stock){
		Hospital h= stock.getHospital();
		Product p =stock.getProduct();
		
		if(p.getProductId()==null || p.getProductId()=="") {
			 p= productRepository.findByProductCategoryAndProductName(
					 p.getProductCategory(), p.getProductName());
			 stock.setProduct(p);
		}
		if(p==null)
			throw new ObjectNotFoundException("Invalid product");
		
		ProductQuantity pq= productStockRepository
				.findByHospitalHospitalIdAndProductProductId(h.getHospitalId(), p.getProductId());
		
		long id= 0;
		
		if(pq!=null)			//stock already exists
			id= pq.getId();
		
		ProductQuantity productQuantity= ProductQuantity.builder()
				.id(id)
				.hospital(stock.getHospital())
				.product(stock.getProduct())
				.cost(stock.getCost())
				.quantity(stock.getQuantity())
				.build();
		
		return productStockRepository.save(productQuantity) != null;
	}

	@Override
	public List<ProductQuantity> findProductQuantityInHospital(String hId) {
		return productStockRepository.findAllByHospitalHospitalId(hId);
	}

	@Override
	public List<Hospital> findAllActiveHospitals() {
		return hospitalRepository.findAllByHospitalStatusTrue();
	}

	@Override
	public boolean manageHospital(String id, String action) {
		Hospital hospital= hospitalRepository.findById(id)
				.orElseThrow(()-> new ObjectNotFoundException("Hospital not found with id: "+id));
		
		if(action==null)
			throw new IllegalArgumentException("Action can't be null");
		
		boolean status= true;
		
		if(!action.equalsIgnoreCase("enable"))
			status= false;
		
		hospital.setHospitalStatus(status);
		
		Hospital updateHospital= hospitalRepository.save(hospital);
		return updateHospital.isHospitalStatus() == status;
	}

	@Override
	public List<Hospital> findAllHospitals() {
		return hospitalRepository.findAll();
	}

	@Override
	public boolean removeProductFromHospital(String hId, String productId) {
		//find all the registered products of a hospital
		ProductQuantity stock= productStockRepository
				.findByHospitalHospitalIdAndProductProductId(hId, productId);
		productStockRepository.delete(stock);
		ProductQuantity tmp= productStockRepository
				.findByHospitalHospitalIdAndProductProductId(hId, productId);
		return tmp==null;
	}

}
