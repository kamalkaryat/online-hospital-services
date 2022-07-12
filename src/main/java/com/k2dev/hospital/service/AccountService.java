package com.k2dev.hospital.service;

import java.util.List;

import com.k2dev.hospital.model.request.ForgotPsw;

public interface AccountService {

	/**
	 * Used for enabling or disabling user account
	 * @param username user login id
	 * @param action enable/disable
	 * @return true on success
	 */
	boolean updateAccount(String username, String action);
	
	/**
	 * Check whether a user already exists or not
	 * @param username login id of user
	 * @return true if exists
	 */
	boolean isExists(String username);
	
	/**
	 * Update the password of a user
	 * @param forgotPsw forgot-password request dto
	 * @return true on success
	 */
	boolean updatePsw(ForgotPsw forgotPsw);
	
	/**
	 * Retrieve a token issued to a particular user
	 * @param username login id of user
	 * @return token
	 */
	String getToken(String username);
}
