package com.k2dev.hospital.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String>{
	private final String constraint= 
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-_!])(?=\\S+$).{8,}$";
	
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		Pattern pattern= Pattern.compile(constraint);
		Matcher matcher= pattern.matcher(password);
		return matcher.find();
	}

}
