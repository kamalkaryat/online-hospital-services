package com.k2dev.hospital.model.request;

import javax.validation.constraints.NotNull;

import com.k2dev.hospital.model.dto.Hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class HospitalAdminSignupRequest extends UserSignupRequest{
	
	@NotNull
	private Hospital hospital;	
}
