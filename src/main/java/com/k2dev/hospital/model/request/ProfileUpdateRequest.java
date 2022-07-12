package com.k2dev.hospital.model.request;

import static com.k2dev.hospital.util.Constants.AREA_ERROR;
import static com.k2dev.hospital.util.Constants.NAME_ERROR;
import static com.k2dev.hospital.util.Constants.PHONE_NO_ERROR;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.k2dev.hospital.model.dto.Area;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.util.BusinessType;
import com.k2dev.hospital.util.Gender;
import com.k2dev.hospital.util.PhoneNo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileUpdateRequest {
	
	private String id;
	private String username;
	
	private LocalDate dob;
	
	@Size(max = 20, message = NAME_ERROR)
	private String name;
	
	@PhoneNo(message = PHONE_NO_ERROR)
	private long phoneNo;
	
	private Gender gender;
	private BusinessType type;
	
	@NotBlank(message = AREA_ERROR)
	private Area area;
	
	private boolean docStatus;
	private String docCategory;
	private String docQualification;
	
	private Hospital hospital;
}
