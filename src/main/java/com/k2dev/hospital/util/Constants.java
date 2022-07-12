package com.k2dev.hospital.util;

public class Constants {
	public static final String NAME_ERROR= "Name must contain 5-20 characters";
	public static final String DOB_ERROR= "Invalid DOB";
	public static final String GENDER_ERROR= "Gender can't be blank";
	public static final String AREA_ERROR= "Enter complete area information";
	public static final String PHONE_NO_ERROR= "Should be 10 digit long & 1st digit must be greater than 5";
	
	public static final String EMAIL_ERROR= "Invalid Email Address";
	public static final String PASSWORD_ERROR= "Invalid password";
	public static final String CONFIRM_PASSWORD_ERROR= "Invalid confirm password";
	public static final String ROLE_ERROR= "Invalid role";
	public static final String STATUS_ERROR= "Enter a valid status";
	public static final String CATEGORY_ERROR= "Invalid doctor category";
	public static final String QUALIFICATION_ERROR= "Invalid doctor qualification";
	
	//hospital entity related errors
	public static final String HOSPITAL_ERROR= "Enter complete hospital information";
	public static final String HOSPITAL_NAME_ERROR= "Hospital name must contain 5-30 characters";
	public static final String HOSPITAL_TYPE_ERROR= "Hospital Type can't be empty";
	public static final String HOSPITALS_NOT_FOUND= "Hospitals are not found";
	public static final String NEW_HOSPITAL_ERROR= "Error while adding hospital";
	public static final String HOSPITAL_DELETE_ERROR= "Error while deleting hospitals";
	
	//Hospital-Admin
	public static final String HOSPITAL_ADMIN_NOT_FOUND= "HospitalAdmin(or s) not found";
	public static final String HOSPITAL_ADMIN_UPDATE_ERROR= "Error while updating hospital-admin";
	public static final String HOSPITAL_ADMIN_REGISTER_ERROR= "Error while registering hospital-admin";
	
	//Product
	public static final String PRODUCT_NOT_FOUND= "Product(or s) not found";
	public static final String PRODUCT_REMOVE_ERROR ="Error while removing the products";
	public static final String PRODUCT_UPDATE_ERROR ="Error while updating the products";
	public static final String PRODUCT_ADDorUPDATE_ERROR ="Error while adding or updating stock";
	public static final String PRODUCT_NOT_PURCHASED= "Proudct Not Purchased";
	public static final String INVALID_PRODUCT_CATEGORY= "Invalid Product-Category";
	public static final String INVALID_PRODUCT_DESCRIPTION= "Product-Description can contain atmost 100 chars";
	public static final String INVALID_PRODUCT_NAME= "Invalid Product-Name";
	public static final String PRODUCT_REGISTER_ERROR= "Error while registering products";
	
	//Doctor
	public static final String DOCTOR_REGISTER_ERROR= "Error while registering doctor";
	public static final String DOCTOR_UPDATE_ERROR= "Error while registering doctor";
	public static final String DOCTOR_REGISTERED= "Doctor registered";
	public static final String DOCTOR_NOT_EXISTS= "Doctors not exists";
	public static final String DOCTORS_NOT_FOUND= "Doctors are not found";
	
	//PATIENT
	public static final String PATIENT_REGISTRATION_ERROR= "Patient Registration Failed";
	public static final String PATIENT_NOT_FOUND= "Patient(or s) Not Found";
	public static final String PATIENT_REGISTERED= "Patient Registered";
	public static final String PATIENT_DELETE_ERROR= "Error while deleting patients";
	public static final String PATIENT_UPDATE_ERROR= "Error while updating patient";
	
	//USER
	public static final String USER_ENABLED= "User(or s) Enabled";
	public static final String USER_NOT_ENABLED= "User(or s) Not Enabled";
	public static final String USER_DISABLED= "User(or s) Disabled";
	public static final String USER_NOT_DISABLED= "User(or s) Not Disabled";
	public static final String USERNAME_NOT_FOUND= "Username Not Found";
	
	//APPOINTMENTS
	public static final String ALL_APPOINTMENT_CLOSE_ERROR= "Error while closing all appointments";
	public static final String ALL_APPOINTMENT_CLOSED= "All appointments are closed now";
	public static final String APPOINTMENT_DELETE_ERROR= "Error while deleting appointments";
	public static final String APPOINTMENT_ADD_ERROR= "Error while adding appointments";
	public static final String APPOINTMENT_RESHCEDULE_ERROR= "Appointment not rescheduled";
	public static final String APPOINTMENT_RESHCEDULED= "Appointment Rescheduled";
	public static final String APPOINTMENT_NOT_CANCELLED= "Appointment Not Cancelled";
	public static final String APPOINTMENT_CANCELLED= "Appointment Cancelled";
	public static final String APPOINTMENT_BOOKING_ERROR= "Error while booking appointment";
	public static final String APPOINTMENT_NOT_FOUND= "No appointments are found";
	
	//AREA
	public static final String STATES_NOT_FOUND_ERROR= "Error while fetching the states";
	public static final String DISTRICTS_NOT_FOUND_ERROR= "Error while fetching the district";
	public static final String PINCODES_NOT_FOUND_ERROR= "Error while fetching the pincodes";
	public static final String AREANAMES_NOT_FOUND_ERROR= "Error while fetching the area-names";
	
	//AUTH
	public static final String INVALID_CREDENTIAL= "Invalid Credential";
	
	
	//PRODUCT-STOCK
	public static final String PRODUCT_STOCK_NOT_FOUND= "Product stock not found";
	
	//TEST
	public static final String TEST_ORDER_ERROR= "Test order not placed";
	
	public static final String NOT_EXISTS= "Not Exists";
	public static final String ALREADY_EXISTS= "Already Exists";
	public static final String INSUFFICIENT_OBJECTS= "Insufficient objects";
	
	//LAB
	public static final String LAB_NOT_FOUND= "Lab(or s) Not Found";
	public static final String LAB_UPDATE_ERROR= "Error while updating lab";
	public static final String LAB_REGISTER_ERROR= "Error while adding new lab";
	public static final String LAB_ACTION_ERROR= "Error either while enabling or disablling lab";
	
	public static final String VALIIDATION_ERROR= "Validation Error";
	public static final String INVALID_INPUT= "Invalid Input Error";
}
