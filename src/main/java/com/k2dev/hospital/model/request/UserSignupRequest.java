package com.k2dev.hospital.model.request;

import static com.k2dev.hospital.util.Constants.DOB_ERROR;
import static com.k2dev.hospital.util.Constants.NAME_ERROR;
import static com.k2dev.hospital.util.Constants.PHONE_NO_ERROR;
import static com.k2dev.hospital.util.Constants.EMAIL_ERROR;
import static com.k2dev.hospital.util.Constants.PASSWORD_ERROR;
import static com.k2dev.hospital.util.Constants.CONFIRM_PASSWORD_ERROR;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.k2dev.hospital.util.Gender;
import com.k2dev.hospital.util.Password;
import com.k2dev.hospital.util.PhoneNo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignupRequest {
	
	@Size(max = 20, message = NAME_ERROR)
	private String name;

	@NotNull(message = DOB_ERROR)
	private LocalDate dob;
	
	@NotNull(message = DOB_ERROR)
	private Gender gender;
	
	@PhoneNo(message = PHONE_NO_ERROR)
	private long phoneNo;
	
	@Email(message = EMAIL_ERROR)
	private String email;
	
	@Password(message = PASSWORD_ERROR)
	private String password;
	
	@Password(message = CONFIRM_PASSWORD_ERROR)
	private String confirmPassword;
	
	private String role;
	
	private boolean status;
}
