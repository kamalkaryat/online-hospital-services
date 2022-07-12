package com.k2dev.hospital.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.AlreadyExistException;
import com.k2dev.hospital.exception.InsufficientObjectsException;
import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.model.dto.Appointment;
import com.k2dev.hospital.model.dto.BookedAppointment;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Patient;
import com.k2dev.hospital.model.request.AppointmentRescheduleRequest;
import com.k2dev.hospital.model.request.AppointmentRequest;
import com.k2dev.hospital.model.response.AppointmentDto;
import com.k2dev.hospital.model.response.AppointmentResponse;
import com.k2dev.hospital.model.response.BookedAppointmentResponse;
import com.k2dev.hospital.repository.AppointmentRepository;
import com.k2dev.hospital.repository.BookedAppointmentRepository;
import com.k2dev.hospital.repository.HospitalRepository;
import com.k2dev.hospital.util.AppointmentDtoConverter;
import com.k2dev.hospital.util.Converter;
import com.k2dev.hospital.util.ObjectFinder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentServiceImp implements AppointmentService{

	private final AppointmentRepository appointmentRepository;
	private final BookedAppointmentRepository bookedAppointmentRepository;
	private final ObjectFinder objectFinder;
	private final Converter converter;
	
	
	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public String bookAppointment(AppointmentRequest request) {

		Hospital hospital= objectFinder.findHospital(request.getHospital());
		if(hospital==null)
			throw new ObjectNotFoundException("Hospital Not Found");
		
		Appointment a= appointmentRepository.findByHospitalHospitalIdAndAppointmentDateAndAppointmentTime(
				hospital.getHospitalId(), request.getDate(), request.getTime());
		
		int available= a.getAppointmentAvailable();
		if(available<1) 
			throw new InsufficientObjectsException("Appointment finished");

		//update current available appointment after booking
		a.setAppointmentAvailable(--available);
		appointmentRepository.save(a);
		
		String bookingId= UUID.randomUUID().toString();
		BookedAppointment bookedAppointment= BookedAppointment.builder()
				.bookingId(bookingId)
				.appointment(a)
				.patient(objectFinder.findPatient(request.getPatientId()))
				.build();
		
		return bookedAppointmentRepository.save(bookedAppointment)!=null ? bookingId : null;
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Override
	public boolean cancelAppointment(String bookingId) {
		BookedAppointment ba= bookedAppointmentRepository.findById(bookingId)
				.orElse(null);			//find booked appointment
		
		if(ba==null)	//invalid booking id
			return false;
		
		Appointment a= ba.getAppointment();
		
		int available= appointmentRepository.findByAppointmentId(a.getAppointmentId());	//finding current available appointments
		//updating available appointments
		a.setAppointmentAvailable(++available);
		
		bookedAppointmentRepository.deleteById(bookingId);		//Canceling the booked appointment
		
		return appointmentRepository.save(a)!=null;
	}
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Override
	public boolean rescheduleAppointment(AppointmentRequest request) {
		System.out.println(request);
		
		Hospital hospital= objectFinder.findHospital(request.getHospital());
		if(hospital==null)
			throw new ObjectNotFoundException("Hospital Not Found");
		
		Appointment oldApt= objectFinder.findAppointmentByBookingId(request.getBookingId());
		int totOld= oldApt.getAppointmentAvailable();
		
		//update new appointment details
		Appointment newApt= appointmentRepository.findByHospitalHospitalIdAndAppointmentDateAndAppointmentTime(
				hospital.getHospitalId(), request.getDate(), request.getTime());
		
		int totNew= newApt.getAppointmentAvailable();
		
		if(totNew<1)
			throw new InsufficientObjectsException("Appointment finished");
		
		//decrement new appointment
		newApt.setAppointmentAvailable(--totNew);
		boolean isNewAptUpdated= appointmentRepository.save(newApt)!=null;
		
		//increment old appointment
		oldApt.setAppointmentAvailable(++totOld);
		boolean isOldAptUpdated= appointmentRepository.save(oldApt)!=null;
		
		Patient p= new Patient();
		p.setPatientId(request.getPatientId());
		
		//update booking info
		BookedAppointment bookedAppointment= BookedAppointment.builder()
				.bookingId(request.getBookingId())
				.appointment(newApt)
				.patient(p)
				.build();
		
		boolean isBooked= bookedAppointmentRepository.save(bookedAppointment)!=null;
		
		return isOldAptUpdated && isNewAptUpdated && isBooked;
	}

	@Override
	public boolean closeAllAppointment(String hospitalId, LocalDate date) {
		List<Appointment> appointments= appointmentRepository.
				findAllByHospitalHospitalIdAndAppointmentDate(hospitalId, date);
		appointments.forEach(a-> a.setAppointmentAvailable(0));
		return appointmentRepository.saveAll(appointments)!=null;
	}

	@Override
	public List<AppointmentResponse> findAppointment(String name, LocalDate date) {
		Hospital hospital= new Hospital();
		hospital.setHospitalName(name);
		Hospital validHospital= objectFinder.findHospital(hospital);
		
		List<Appointment> appointments= appointmentRepository.
				findAllByHospitalHospitalIdAndAppointmentDate(validHospital.getHospitalId(), date);
		if(appointments==null)
			return null;
		return converter.toAptResponse(appointments);
	}

	@Override
	public boolean deleteAppointment(String id) {
		appointmentRepository.deleteById(id);
		return true;
	}

	@Override
	public Appointment addAppointment(Appointment appointment) {
		
		Appointment a= appointmentRepository.checkAlreadyExistAppointment(
				appointment.getHospital().getHospitalId(), appointment.getAppointmentDate(), 
				appointment.getAppointmentTime());
		
		//if-appointment already exists
		if(a!=null)
			throw new AlreadyExistException("Appointment already exists");
		
		appointment.setAppointmentId(UUID.randomUUID().toString());
		
		//appointmentsAvailable= totalAppointments when new appointment added
		int total= appointment.getTotalAppointments();
		appointment.setAppointmentAvailable(total);
			
		return appointmentRepository.save(appointment);
	}

	@Override
	public List<BookedAppointmentResponse> findBookedAppointments(String patientId) {
		return converter.toBookedAptResponse(
				bookedAppointmentRepository.findAllByPatientPatientId(patientId));
	}
	
	@Override
	public List<Appointment> findAppointmentsInHospital(String hospitalId){
		return appointmentRepository.findAllByHospitalHospitalId(hospitalId);
	}
	
}
