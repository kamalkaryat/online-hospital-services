package com.k2dev.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k2dev.hospital.model.dto.HospitalAdmin;

public interface HospitalAdminRepository extends JpaRepository<HospitalAdmin, String> {
	/**
	 * Find HospitalAdmin by it's username
	 * @param username hospital-admin username
	 * @return true if exists
	 */
	boolean existsHospitalAdminByLoginUsername(final String username);

	/**
	 * Find hospital-admin by its username
	 * @param username username of hospital-admin
	 * @return hospital-admin
	 */
	HospitalAdmin findByLoginUsername(String username);
}
