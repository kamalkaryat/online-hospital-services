package com.k2dev.hospital.service;

import java.time.LocalDate;
import java.util.List;

import com.k2dev.hospital.model.dto.Appointment;
import com.k2dev.hospital.model.dto.BookedAppointment;
import com.k2dev.hospital.model.request.AppointmentRescheduleRequest;
import com.k2dev.hospital.model.request.AppointmentRequest;
import com.k2dev.hospital.model.response.AppointmentDto;
import com.k2dev.hospital.model.response.AppointmentResponse;
import com.k2dev.hospital.model.response.BookedAppointmentResponse;

public interface AppointmentService {
	
	/**
	 * Patient can book an 1 or more appointment in a hospital but not at the same time 
	 * @param  appointment request
	 * @return booking id
	 */
	String bookAppointment(AppointmentRequest request);
	
	/**
	 * Patient can cancel an appointment using the 
	 * @param request appointment request
	 * @return true on success
	 */
	boolean cancelAppointment(String bookingId);
	
	/**
	 * In emergency like situation a Admin of a hospital can close all the
	 * appointments of his/her corresponding hospital
	 * @param hospitalId to shutdown all appointments
	 * @param date date for which hAdmin wants to close all appointments
	 * @return true on success
	 */
	boolean closeAllAppointment(String hospitalId, LocalDate date);
	
	/**
	 * Used for finding all the appointments which available in a specific hospital
	 * @param hospitalId hospital where it will find appointments
	 * @param date date on which user is looking for appointments
	 * @return list of appointments currently available
	 */
	List<AppointmentResponse> findAppointment(String hospitalId, LocalDate date);
	
	/**
	 * Patient can reschedule their existing appointment (or s).
	 * Release existing one & book new one if available
	 * Existing appointment get cancelled only when new appointment booked successfully
	 * @param request reschedule request
	 * @return true on success
	 */
	boolean rescheduleAppointment(AppointmentRequest request);
	
	/**
	 * Admin of a hospital can delete a appointment according to their
	 * business requirements.
	 * @param id appointment id
	 * @return true on success
	 */
	boolean deleteAppointment(String id);
	
	/**
	 * Admin of a hospital can add a appointment according to their
	 * business requirements.
	 * @param appointments to add into database
	 * @return persisted appointment object
	 */
	Appointment addAppointment(Appointment appointment);
	
	/**
	 * Find all the booked appointments of a patient
	 * @param patientId patient id
	 * @return list of booked appointments
	 */
	List<BookedAppointmentResponse> findBookedAppointments(String patientId);

	/**
	 * Find all appointments in a hospital
	 * @param hospitalId id of hospital
	 * @return list of appointment
	 */
	List<Appointment> findAppointmentsInHospital(String hospitalId);
}
