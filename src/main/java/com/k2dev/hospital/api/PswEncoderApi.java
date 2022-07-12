package com.k2dev.hospital.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/psw")
public class PswEncoderApi {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/{psw}")
	public String encode(@PathVariable String psw) {
		return passwordEncoder.encode(psw);
	}
}
