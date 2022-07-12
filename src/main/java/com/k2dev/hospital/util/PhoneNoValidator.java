package com.k2dev.hospital.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * Phone number validator
 * @author kamal
 * check whether a phone number is starting from either 7,8,9
 * and it should contain 10 digits
 */
public class PhoneNoValidator implements ConstraintValidator<PhoneNo, Long> {
	private final String constraint= "[6-9]{1}[0-9]{9}";
	
	@Override
	public boolean isValid(Long phoneNo, ConstraintValidatorContext context) {	
		Pattern pattern= Pattern.compile(constraint);
		Matcher matcher= pattern.matcher(phoneNo+"");
		return matcher.find();
	}

}
