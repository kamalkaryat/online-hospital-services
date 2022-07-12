package com.k2dev.hospital.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationRequest {
	
	@Size(min= 5, max= 50, message = "Username can't be empty")
	private String username;
	
	@Size(min= 3, max = 20, message = "Password must contain 3-20 characters")
	private String password;
}
