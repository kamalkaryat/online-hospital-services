package com.k2dev.hospital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.AlreadyExistException;
import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.request.DoctorSignupRequest;
import com.k2dev.hospital.model.request.HospitalAdminSignupRequest;
import com.k2dev.hospital.repository.DoctorRepository;
import com.k2dev.hospital.util.Converter;
import com.k2dev.hospital.util.ObjectSpecificationBuilder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorServiceImp implements DoctorService {

	private final DoctorRepository doctorRepository;
	private final AccountService accountService;
	private final Converter converter;
	
	public List<Doctor> findDoctorsByFilter(String filters) {
		ObjectSpecificationBuilder builder= new ObjectSpecificationBuilder(); 
        
		/*	Format for a constraint in a filters query
         *	key
         *	operation: :/</>
         *	value
         *	comma(,): delimiter between 2 constraints
         * 	*/
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([^#$!<>,/?]+[A-Za-z0-9@-_]?),");
        Matcher matcher = pattern.matcher(filters.trim() + ",");	
        while (matcher.find()) {
        	System.out.println("matching pattern");
        	//1: key, 2: operation, 3: value
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        
        Specification<Doctor> spec = builder.build();
        return doctorRepository.findAll(spec);
	}

	@Override
	public boolean saveDoctor(DoctorSignupRequest request) {
		if(request.getEmail()!=null) {
			boolean isExists= accountService.isExists(request.getEmail());
			if(isExists)
				throw new AlreadyExistException("doctor already exists with username: "+request.getEmail());
		}
		return doctorRepository.save(converter.toDoctor(request))!=null;
	}

	@Override
	public Doctor updateDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}
	
	@Override
	public boolean deleteDoctor(String id) {
		doctorRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Doctor> findAllActiveDoctors() {
		return doctorRepository.findAllByLoginEnabledTrue();
	}

	@Override
	public List<Doctor> findAllDoctors() {
		return doctorRepository.findAll();
	}

	@Override
	public List<Doctor> findAllDoctorsInHospital(String id) {
		List<Doctor> doctors= doctorRepository.findAllByLoginEnabledTrue();
		List<Doctor> filteredDoctors= new ArrayList<>();
		for(Doctor doctor: doctors) {
			Hospital h= doctor.getHospital();
			if(h.getHospitalId().equals(id))
				filteredDoctors.add(doctor);
		}
		return filteredDoctors;
	}
}
