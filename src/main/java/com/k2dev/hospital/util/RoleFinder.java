package com.k2dev.hospital.util;

import org.springframework.stereotype.Component;

import com.k2dev.hospital.model.dto.Role;
import com.k2dev.hospital.repository.RoleRepository;

import lombok.NoArgsConstructor;

@Component
public class RoleFinder {

	private final RoleRepository roleRepository;
	
	public RoleFinder(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	
	public Role getRole(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}
}
