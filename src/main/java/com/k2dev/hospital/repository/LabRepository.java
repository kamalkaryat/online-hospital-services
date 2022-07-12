package com.k2dev.hospital.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.k2dev.hospital.model.dto.Lab;

public interface LabRepository extends JpaRepository<Lab, String> {
	
	/**
	 * Find all the labs which are currently enabled
	 * @return list of labs
	 */
	List<Lab> findAllByHospitalHospitalStatusTrueAndLabStatusTrue();
	
	/**
	 * Check whether a lab already exists in a hospital or not
	 * @param hosptialId id of hospial
	 * @param labName name of lab
	 * @return true if exists
	 */
	boolean existsByHospitalHospitalIdAndLabName(String hosptialId, String labName);
	
	Lab findByLabNameAndHospitalHospitalName(String labName, String hospitalName);
	
}
