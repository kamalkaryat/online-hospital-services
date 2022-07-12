package com.k2dev.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k2dev.hospital.model.dto.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Role findByRoleName(String roleName);
}
