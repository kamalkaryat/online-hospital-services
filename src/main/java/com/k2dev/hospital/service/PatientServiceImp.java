package com.k2dev.hospital.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.AlreadyExistException;
import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Lab;
import com.k2dev.hospital.model.dto.Login;
import com.k2dev.hospital.model.dto.Patient;
import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.dto.TestOrder;
import com.k2dev.hospital.model.dto.TestOrderResponse;
import com.k2dev.hospital.model.request.PatientSignupRequest;
import com.k2dev.hospital.model.request.ProfileUpdateRequest;
import com.k2dev.hospital.model.request.TestRequest;
import com.k2dev.hospital.model.response.PurchasedProductResponse;
import com.k2dev.hospital.repository.LabRepository;
import com.k2dev.hospital.repository.LoginRepository;
import com.k2dev.hospital.repository.PatientRepository;
import com.k2dev.hospital.repository.ProductRepository;
import com.k2dev.hospital.repository.PurchasedProductRepository;
import com.k2dev.hospital.repository.TestOrderRepository;
import com.k2dev.hospital.util.Converter;
import com.k2dev.hospital.util.ObjectFinder;
import com.k2dev.hospital.util.TestStatus;
import com.k2dev.hospital.util.TestOrderResponseMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientServiceImp implements PatientService{

	private final PatientRepository patientRepository;
	private final TestOrderRepository testOrderRepository;
	private final ProductRepository productRepository;
	private final Converter converter;
	private final AccountService accountService;
	private final ObjectFinder objectFinder;
	
	@Override
	public boolean savePatient(PatientSignupRequest request) {
		if(request.getEmail()!=null) {
			boolean isExists= accountService.isExists(request.getEmail());
			if(isExists)
				throw new AlreadyExistException("patient already exists exception");
		}
		Patient patient= converter.toPatient(request);
		return patientRepository.save(patient)!=null;
	}
	
	@Override
	public List<Patient> findAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public Patient findPatient(String id) {
		return patientRepository.findById(id).get();
	}

	@Override
	public boolean deletePatients(List<String> ids) {
		patientRepository.deleteAllById(ids);
		return true;
	}

	@Override
	public String placeTestRequest(TestRequest request) throws NotFoundException {
		
		Product product= productRepository.findByProductCategoryAndProductName(
				request.getProduct().getProductCategory(), request.getProduct().getProductName());
		
		Hospital hospital= request.getLab().getHospital();
		
		Lab lab= objectFinder.findLab(request.getLab().getLabName(), hospital.getHospitalName());
		if(lab==null)
			throw new ObjectNotFoundException("Lab Not Found");
		
		Patient p= new Patient();
		p.setPatientId(request.getPatientId());
		
		String orderId= UUID.randomUUID().toString();
		TestOrder testOrder= TestOrder.builder()
				.orderId(orderId)
				.patient(p)
				.product(product)
				.orderDateTime(LocalDateTime.now())
				.lab(lab)
				.status(TestStatus.REQUEST_SUBMITTED)
				.build();
		return testOrderRepository.save(testOrder)!=null ? orderId: null;
	}
	
	@Override
	public List<TestOrderResponse> findPatientTestRequests(String patientId) throws NotFoundException{
		List<TestOrder> list= testOrderRepository.findAllByPatientPatientId(patientId);
		if(list == null) 
			throw new NotFoundException();
		return TestOrderResponseMapper.convert(list);
	}

	@Override
	public Patient updateProfile(ProfileUpdateRequest request) {
		return patientRepository.save(converter.toPatient(request));
		
	}
}
