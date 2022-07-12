package com.k2dev.hospital.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.k2dev.hospital.model.dto.Login;

public interface LoginRepository extends JpaRepository<Login, String> {
	
	/**
	 * Check whether a user exists by its username
	 * @param username username of user
	 * @return true if exists
	 */
	boolean existsById(String username);
	
	/**
	 * Update login details of an user
	 * @param enabled status of user
	 * @param username username of user
	 */
	
	Login findByUsernameAndEnabledTrue(String username);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE Login l SET l.password= :password WHERE l.username= :username")
	void updatePassword(@Param("username") String username, @Param("password") String password);  
}
