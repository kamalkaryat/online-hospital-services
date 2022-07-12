package com.k2dev.hospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.jwt.JwtUtil;
import com.k2dev.hospital.model.dto.Login;
import com.k2dev.hospital.model.request.AuthenticationRequest;
import com.k2dev.hospital.model.request.ForgotPsw;
import com.k2dev.hospital.model.response.JwtResponse;
import com.k2dev.hospital.util.EmailDetails;
import com.k2dev.hospital.util.ObjectFinder;

import lombok.AllArgsConstructor;

@Service

public class AuthenticationService {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ObjectFinder objectFinder;
	
	private int code= -1;
	
	public JwtResponse doAuthentication(AuthenticationRequest request) {
		 Authentication authentication = authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(request.getUsername(),
			        		request.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = jwtUtil.generateJwtToken(authentication);
	    
	    //Login login = (Login) authentication.getPrincipal();
	    User user = (User) authentication.getPrincipal();
	    List<String> roles = user.getAuthorities().stream()
	        .map(item -> item.getAuthority())
	        .collect(Collectors.toList());
	
	    com.k2dev.hospital.model.response.User u= objectFinder.findUser(user.getUsername(), roles.get(0));
	    u.setUsername(user.getUsername());
	    u.setRoles(roles);
	    
	    return JwtResponse.builder()
	    		.token(jwt)
	    		.user(u)
	    		.build();
	}

	public void sendCode(String username) {
		EmailDetails emailDetails= EmailDetails.builder()
				.recipient(username)
				.build();
		code= emailService.sendMail(emailDetails);
		System.out.println("Code: "+code);
	}
	
	public boolean resetPsw(ForgotPsw forgotPsw) {	
		if(forgotPsw.getCode()==-1 || code!=forgotPsw.getCode())
			throw new IllegalArgumentException("Invalid code: "+forgotPsw.getCode());
		
		return accountService.updatePsw(forgotPsw);
	}
}
