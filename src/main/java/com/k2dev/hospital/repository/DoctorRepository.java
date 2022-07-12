package com.k2dev.hospital.repository;
import java.sql.PseudoColumnUsage;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.k2dev.hospital.model.dto.Doctor;

public interface DoctorRepository extends 
		JpaRepository<Doctor, String>, JpaSpecificationExecutor<Doctor>{
	
	/**
	 * Find all doctors which are currently active
	 * @return list of active doctors
	 */
	List<Doctor> findAllByLoginEnabledTrue();

	/**
	 * Find doctor by its username
	 * @param username username of doctor
	 * @return doctor
	 */
	Doctor findByLoginUsername(String username);

}
