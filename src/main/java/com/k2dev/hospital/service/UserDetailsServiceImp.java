package com.k2dev.hospital.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.model.dto.Login;
import com.k2dev.hospital.repository.LoginRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

	private final LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Login login= loginRepository.findById(username).orElseThrow(()->
				new UsernameNotFoundException("User not found with username: "+username));
		
		Collection<SimpleGrantedAuthority> authorities= new HashSet<>();
		login.getRoles().forEach(role->{
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});
		return new User(login.getUsername(), login.getPassword(), authorities);
	}

}
