package com.k2dev.hospital.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.AlreadyExistException;
import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.exception.UserNotFoundException;
import com.k2dev.hospital.model.dto.HospitalAdmin;
import com.k2dev.hospital.model.dto.Role;
import com.k2dev.hospital.model.dto.TestOrder;
import com.k2dev.hospital.model.request.HospitalAdminSignupRequest;
import com.k2dev.hospital.model.request.ProfileUpdateRequest;
import com.k2dev.hospital.repository.HospitalAdminRepository;
import com.k2dev.hospital.repository.TestOrderRepository;
import com.k2dev.hospital.util.Converter;
import com.k2dev.hospital.util.RoleFinder;
import com.k2dev.hospital.util.RoleType;
import com.k2dev.hospital.util.TestStatusConverter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HospitalAdminServiceImp implements HospitalAdminService {

	private final HospitalAdminRepository hospitalAdminRepository;
	private final Converter converter;
	private final TestOrderRepository testOrderRepository;
	
	@Override
	public List<HospitalAdmin> findHospitalAdmins() {
		return hospitalAdminRepository.findAll();
	}

	@Override
	public HospitalAdmin findHospitalAdmin(String id) {
		return hospitalAdminRepository.findById(id).
				orElseThrow(()-> new UserNotFoundException("HospitalAdmin not found"));
	}

	@Override
	public boolean saveHospitalAdmin(HospitalAdminSignupRequest signupRequest) {
		System.out.println("inside HA-Service");
		boolean isExists= hospitalAdminRepository.existsHospitalAdminByLoginUsername(
				signupRequest.getEmail());
		
		if(isExists)
			throw new AlreadyExistException("Hospital-Admin exists with id: "
						+signupRequest.getEmail());
		
		return hospitalAdminRepository.save(converter.toHospitalAdmin(signupRequest))!=null;
	}

	@Override
	public HospitalAdmin updateHospitalAdmin(ProfileUpdateRequest updateRequest) {
		return hospitalAdminRepository.save(converter.toHospitalAdmin(updateRequest));
	}

	@Override
	public boolean updateTestOrder(String id, String status) {
		testOrderRepository.updateTestStatus(id, new TestStatusConverter().convert(status));
		TestOrder testOrder= testOrderRepository.findById(id).orElseThrow(
				()-> new ObjectNotFoundException("TestOrder not found with id: "+id));
		String newStatus= testOrder.getStatus().toString();
		
		return newStatus.equalsIgnoreCase(status);
	}

	@Override
	public List<TestOrder> findTestOrders(String id) {
		return testOrderRepository.findAllByLabHospitalHospitalId(id);
	}

}
