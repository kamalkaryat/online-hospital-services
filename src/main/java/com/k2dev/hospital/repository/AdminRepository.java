package com.k2dev.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k2dev.hospital.model.dto.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{

	/**
	 * Find admin by it's username
	 * @param username username of admin
	 * @return admin
	 */
	Admin findByLoginUsername(String username);

}
