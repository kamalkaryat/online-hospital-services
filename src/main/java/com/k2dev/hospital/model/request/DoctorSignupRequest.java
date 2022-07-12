package com.k2dev.hospital.model.request;

import static com.k2dev.hospital.util.Constants.*;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.util.Gender;
import com.k2dev.hospital.util.Password;
import com.k2dev.hospital.util.PhoneNo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSignupRequest extends UserSignupRequest {
	
	@NotNull
	private Hospital hospital;
	
	@NotBlank(message = QUALIFICATION_ERROR)
	private String qualification;
	
	@NotBlank(message = CATEGORY_ERROR)
	private String department;
}
