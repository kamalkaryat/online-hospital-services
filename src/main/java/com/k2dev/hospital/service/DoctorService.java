package com.k2dev.hospital.service;

import java.util.List;

import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.model.request.DoctorSignupRequest;

public interface DoctorService {
	
	/**
	 * Search doctors by applying the filters provided by the user
	 * Comma(,) is used as a delimiter for separating the filters.
	 * e.g: http://localhost:8080/users?filters=lastName:doe,age>25
	 * @param filters string contain all filters, set by the user
	 * @return list of doctors
	 */
	List<Doctor> findDoctorsByFilter(String filters);
	
	/**
	 * Add doctor into database
	 * @param request doctor signup request
	 * @return true for success
	 */
	boolean saveDoctor(DoctorSignupRequest request);

	/**
	 * Delete doctor from database 
	 * @param id doctor id
	 * @return true for success else false
	 */
	boolean deleteDoctor(String id);

	/**
	 * Update a doctor
	 * @param doctor
	 * @return updated instance of doctor
	 */
	Doctor updateDoctor(Doctor doctor);
	
	/**
	 * Find all the active doctors. Used by normal users
	 * @return list of active doctors
	 */
	List<Doctor> findAllActiveDoctors();
	
	/**
	 * Find all the doctors. Used by admin user
	 * @return list of all doctors
	 */
	List<Doctor> findAllDoctors();

	/**
	 * Find all doctors in a hospital
	 * @param id hospital id
	 * @return list of doctors
	 */
	List<Doctor> findAllDoctorsInHospital(String id);
}
