package com.k2dev.hospital.model.response;

import java.util.List;

import com.k2dev.hospital.model.dto.Area;
import com.k2dev.hospital.model.dto.Hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class User {
	private String id;
	private String username;
	private boolean enabled;
	private Area area;
	private Hospital hospital;
	private List<String> roles;
}
