package com.k2dev.hospital.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.k2dev.hospital.model.dto.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, String> {
	/**
	 * Find all the hospitals which are currently enabled
	 * @return list of hospitals
	 */
	List<Hospital> findAllByHospitalStatusTrue();
	
	/**
	 * Find all the hospitals which are currently disabled
	 * @return list of hospitals
	 */
	List<Hospital> findAllByHospitalStatusFalse();
	
	/**
	 * Find hospital by its name
	 * @param hospitalName name of the hospital
	 * @return hospital if found
	 */
	Hospital findByHospitalName(String hospitalName);
	
	/**
	 * Find hospital by its name & a state
	 * @param state name of state
	 * @param hospitalName name of hospital
	 * @return hospital
	 */
	Hospital findByAreaStateAndHospitalName(String state, String hospitalName);
	
	/**
	 * Change the status of a hospital
	 * @param hospitalId id of hospital
	 * @param status either true or false
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Hospital h SET h.hospitalStatus= :status WHERE h.hospitalId= :hospitalId")
	void updateHospital(String hospitalId, boolean status);
}
