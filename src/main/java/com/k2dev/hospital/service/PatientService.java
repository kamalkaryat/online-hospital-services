package com.k2dev.hospital.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.k2dev.hospital.model.dto.Patient;
import com.k2dev.hospital.model.dto.TestOrder;
import com.k2dev.hospital.model.dto.TestOrderResponse;
import com.k2dev.hospital.model.request.PatientSignupRequest;
import com.k2dev.hospital.model.request.ProfileUpdateRequest;
import com.k2dev.hospital.model.request.TestRequest;
import com.k2dev.hospital.model.response.PurchasedProductResponse;

public interface PatientService {
	/**
	 * Register a patient
	 * @param request patient signup request
	 * @return flag
	 */
	boolean savePatient(PatientSignupRequest request);
	
	/**
	 * Update patient profile
	 * @param patient profile update request
	 * @return updated patient
	 */
	Patient updateProfile(ProfileUpdateRequest patient);
	
	
	
	/**
	 * Fetch all the patients from database
	 * @return list of patients
	 */
	List<Patient> findAllPatients();
	/**
	 * Find a specific patient based on its id
	 * @param id patient id
	 * @return
	 */
	Patient findPatient(String id);
	
	/**
	 * Remove 1 or more patients from database 
	 * @param patients list for patient ids
	 * @return flag
	 */
	boolean deletePatients(List<String> patients);
	
	/**
	 * Order for a home test, if it is possible 
	 * @param testRequest test order related information
	 * @return string order id
	 * @throws NotFoundException if patient/lab/test are not found
	 */
	String placeTestRequest(TestRequest testRequest) throws NotFoundException;
	
	/**
	 * Find all the test requests made by a particular patient
	 * @param patientId id of patient
	 * @return list of patient test request
	 * @exception patient doesn't has any test
	 */
	List<TestOrderResponse> findPatientTestRequests(String patientId) throws NotFoundException;
}
