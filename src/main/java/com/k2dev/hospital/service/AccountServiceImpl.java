package com.k2dev.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.exception.UserNotFoundException;
import com.k2dev.hospital.model.dto.Login;
import com.k2dev.hospital.model.request.ForgotPsw;
import com.k2dev.hospital.repository.LoginRepository;
import com.k2dev.hospital.service.AccountService;
import com.k2dev.hospital.util.Converter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
	
	private final LoginRepository loginRepository;	
	private final Converter converter;
	
	public boolean updateAccount(String username, final String action) {
		Login login= loginRepository.findById(username).orElseThrow(
				()-> new UserNotFoundException("user not exists with username: "+username));
		//enable the given accounts
		
		boolean enable= false;
		if(action.equalsIgnoreCase("enable"))
			enable= true;
		login.setEnabled(enable);
		
		Login updatedLogin= loginRepository.save(login);
		return  updatedLogin.isEnabled() == enable; 
	}


	@Override
	public boolean isExists(String username) {
		return loginRepository.existsById(username);
	}


	@Override
	public boolean updatePsw(ForgotPsw forgotPsw) {
		Login login= loginRepository.findById(forgotPsw.getUsername()).orElseThrow(
				()-> new ObjectNotFoundException("user not exists with username: "+forgotPsw.getUsername()));
		login.setPassword(converter.encodePsw(forgotPsw.getPsw()));
		
		return loginRepository.save(login)!=null;
	}


	//TODO
	@Override
	public String getToken(final String username) {
		return null;
	}
}
