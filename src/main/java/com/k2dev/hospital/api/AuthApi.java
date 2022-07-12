package com.k2dev.hospital.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k2dev.hospital.exception.UserNotFoundException;
import com.k2dev.hospital.model.request.AuthenticationRequest;
import com.k2dev.hospital.model.request.ForgotPsw;
import com.k2dev.hospital.model.response.JwtResponse;
import com.k2dev.hospital.service.AuthenticationService;
import com.k2dev.hospital.util.ObjectFinder;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.AllArgsConstructor;

import static com.k2dev.hospital.util.Constants.INVALID_CREDENTIAL;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthApi {

	private final AuthenticationService authenticationService;
	private final ObjectFinder objectFinder;
	
	@PostMapping("/login")
	public ResponseEntity<?> auth(@Valid @RequestBody AuthenticationRequest request) {
		JwtResponse response= authenticationService.doAuthentication(request);
		if(response==null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_CREDENTIAL);
		return ResponseEntity.ok().body(response);
		
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody String username){
		authenticationService.sendCode(username);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/forgotPsw")
	public ResponseEntity<?> forgotPsw(@RequestBody ForgotPsw forgotPsw){
		boolean isChanged= authenticationService.resetPsw(forgotPsw);
		if(!isChanged)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Password Not Changed");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/currentUser")
	public ResponseEntity<?> currentLoggedInUser(Principal principal){
		UserDetails userDetails= (UserDetails)principal;
		if(userDetails==null)
			throw new UserNotFoundException("Current User Not Found");
		
		String username= userDetails.getUsername();
		if(username==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		return ResponseEntity.status(HttpStatus.OK).body(username);
	}
}
