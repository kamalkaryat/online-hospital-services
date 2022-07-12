package com.k2dev.hospital.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.k2dev.hospital.util.Constants.AREA_ERROR;

import javax.validation.constraints.NotNull;

import com.k2dev.hospital.model.dto.Area;;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PatientSignupRequest  extends UserSignupRequest{
	
	@NotNull(message = AREA_ERROR)
	private Area area;
}
