package com.k2dev.hospital.service;

import java.util.List;

import com.k2dev.hospital.model.dto.HospitalAdmin;
import com.k2dev.hospital.model.dto.TestOrder;
import com.k2dev.hospital.model.request.HospitalAdminSignupRequest;
import com.k2dev.hospital.model.request.ProfileUpdateRequest;

public interface HospitalAdminService {
	/**
	 * Find all the hosptial-admins of all hospital
	 * This function must be called by a admin user
	 * @return list of hospital-admins
	 */
	List<HospitalAdmin> findHospitalAdmins();
	
	/**
	 * Find a hospital-admin based on his id
	 * @param id hospital-admin id
	 * @return hospital-admin if exists
	 */
	HospitalAdmin findHospitalAdmin(String id);
	
	/**
	 * Save a hospital-admin into the databse
	 * @param signupRequest hospital-admin info
	 * @return true on success
	 */
	boolean saveHospitalAdmin(HospitalAdminSignupRequest signupRequest);
	
	/**
	 * Update a hospital-admin
	 * @param request updated information of hospital-admin
	 * @return updated hospital-admin
	 */
	HospitalAdmin updateHospitalAdmin(ProfileUpdateRequest request);
	
	/**
	 * Update the current status of a test-order made by a patient
	 * @param id test order id
	 * @param status new status
	 * @return true on success
	 */
	boolean updateTestOrder(String id, String status);

	/**
	 * Find all the test orders of a hospital
	 * @param id hospital id
	 * @return list of test orders
	 */
	List<TestOrder> findTestOrders(String id);
}
