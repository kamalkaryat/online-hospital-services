package com.k2dev.hospital.model.request;

import javax.validation.constraints.Email;

import com.k2dev.hospital.util.Password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPsw {
	@Email
	private String username;
	
	private int code;
	
	@Password
	private String psw;
	
	@Password
	private String cnfPsw;
}
