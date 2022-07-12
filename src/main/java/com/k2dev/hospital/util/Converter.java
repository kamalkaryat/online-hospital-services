package com.k2dev.hospital.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.model.dto.Appointment;
import com.k2dev.hospital.model.dto.Area;
import com.k2dev.hospital.model.dto.BookedAppointment;
import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.HospitalAdmin;
import com.k2dev.hospital.model.dto.Login;
import com.k2dev.hospital.model.dto.Patient;
import com.k2dev.hospital.model.dto.Role;
import com.k2dev.hospital.model.request.DoctorSignupRequest;
import com.k2dev.hospital.model.request.HospitalAdminSignupRequest;
import com.k2dev.hospital.model.request.PatientSignupRequest;
import com.k2dev.hospital.model.request.ProfileUpdateRequest;
import com.k2dev.hospital.model.response.AppointmentResponse;
import com.k2dev.hospital.model.response.BookedAppointmentResponse;
import com.k2dev.hospital.repository.AreaRepository;
import com.k2dev.hospital.repository.HospitalRepository;
import com.k2dev.hospital.repository.LoginRepository;
import com.k2dev.hospital.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Converter {
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AreaRepository areaRepository;
	
	
	private final LoginRepository loginRepository;
	
	private final ObjectFinder objectFinder;
	
	//convert PatientSingnupRequest to Patient entity
	public Patient toPatient(PatientSignupRequest request) {	
		
		String psw= request.getPassword();
		String cnfPsw= request.getConfirmPassword();
		if(!isValidPassword(psw, cnfPsw))
			throw new IllegalArgumentException("Invalid Password: Password & Confirm password not matching");
		
		Area area= request.getArea();
		if(area==null)
			throw new IllegalArgumentException("Area Can't be Null");
		
		String areaName= area.getAreaName();
		int pincode= area.getPincode();
		
		//set the areaId into area object
		area.setAreaId(findAreaId(areaName, pincode));
		
		return Patient.builder()
				.patientId(UUID.randomUUID().toString())
				.patientName(request.getName())
				.patientDob(request.getDob())
				.patientGender(request.getGender())
				.patientPhoneNo(request.getPhoneNo())
				.area(area)
				.login(toLogin(request.getEmail(), request.getPassword(), "PATIENT", true))
				.build();
	}
	
	//convert ProfileUpdateRequest to Patient entity
	public Patient toPatient(ProfileUpdateRequest request) {
		Area area= request.getArea();
		if(area==null)
			throw new IllegalArgumentException("Area can't be null");
		
		String areaName= area.getAreaName();
		int pincode= area.getPincode();
		
		//set the areaId into area object
		area.setAreaId(findAreaId(areaName, pincode));
		
		Login login= loginRepository.findById(request.getUsername())
				.orElseThrow(()-> new UsernameNotFoundException(
						"user not found with username: "+request.getUsername()));
		
		return Patient.builder()
				.patientId(request.getId())
				.patientName(request.getName())
				.patientDob(request.getDob())
				.patientGender(request.getGender())
				.patientPhoneNo(request.getPhoneNo())
				.area(area)
				.login(login)
				.build();
	}
	
	//convert update-request to HospitalAdmin entity
	public HospitalAdmin toHospitalAdmin(ProfileUpdateRequest request) {	
		
		Hospital hospital= request.getHospital();
		Area area= request.getArea();
		
		if(hospital==null)
			throw new IllegalArgumentException("Hospital Can't be Null");
		
		if(area==null)
			throw new IllegalArgumentException("Area Can't be Null");
		
		hospital= objectFinder.findHospital(hospital);
		
		Login login= loginRepository.findById(request.getUsername())
				.orElseThrow(()-> new UsernameNotFoundException(
						"user not found with username: "+request.getUsername()));
		
		return HospitalAdmin.builder()
				.hospitalAdminId(request.getId())
				.hospitalAdminName(request.getName())
				.hospitalAdminDob(request.getDob())
				.hospitalAdminGender(request.getGender())
				.hospitalAdminPhoneNo(request.getPhoneNo())
				.hospital(hospital)
				.login(login)
				.build();
	}
	
	private int findAreaId(String areaName, int pincode) {
		return areaRepository.findByAreaNameAndPincode(areaName, pincode);
	}
	
	//convert DoctorSignupRequest to doctor entity
	public Doctor toDoctor(DoctorSignupRequest request) {
		String psw= request.getPassword();
		String cnfPsw= request.getConfirmPassword();
		if(!isValidPassword(psw, cnfPsw))
			throw new IllegalArgumentException("Invalid Password: Password & Confirm password not matching");
		
		Hospital hospital= request.getHospital();
		if(hospital==null)
			throw new IllegalArgumentException("hospital can't be null");
		
		Area area= hospital.getArea();
		if(area==null)
			throw new IllegalArgumentException("area can't be null");
		
		Hospital validHospital= objectFinder.findHospital(hospital);
		if(validHospital==null)
			throw new IllegalArgumentException("invalid hospital");
		
		
		return Doctor.builder()
				.doctorId(UUID.randomUUID().toString())
				.doctorName(request.getName())
				.doctorDob(request.getDob())
				.doctorGender(request.getGender())
				.doctorDept(request.getDepartment())
				.doctorPhoneNo(request.getPhoneNo())
				.doctorQualification(request.getQualification())
				.doctorStatus(request.isStatus())
				.login(toLogin(request.getEmail(), request.getPassword(), "DOCTOR", true))
				.hospital(validHospital)
				.build();
	}
	
	//convert HospitalAdminSignupRequest to HospitalAdmin entity
	public HospitalAdmin toHospitalAdmin(HospitalAdminSignupRequest request) {
		
		String psw= request.getPassword();
		String cnfPsw= request.getConfirmPassword();
		if(!isValidPassword(psw, cnfPsw))
			throw new IllegalArgumentException("Invalid Password: Password & Confirm password not matching");
		
		Hospital hospital= request.getHospital();
		if(hospital==null)
			throw new IllegalArgumentException("toHospitalAdmin: Hospital Can't be Null");
		
		Area area= hospital.getArea();	
		if(area==null)
			throw new IllegalArgumentException("toHospitalAdmin: Area Can't be Null");
		
		Hospital validHospital= objectFinder.findHospital(hospital);
		if(validHospital==null)
			throw new IllegalArgumentException("invalid hospital");
		
		Login login= toLogin(request.getEmail(), request.getPassword(), "HOSPITAL_ADMIN", true);
		return HospitalAdmin.builder()
				.hospitalAdminId(UUID.randomUUID().toString())
				.hospitalAdminName(request.getName())
				.hospitalAdminDob(request.getDob())
				.hospitalAdminGender(request.getGender())
				.hospital(validHospital)
				.hospitalAdminPhoneNo(request.getPhoneNo())
				.login(login)
				.build();			
	}
	
	//convert to Login entity
	private Login toLogin(String username, String password, String roleName, boolean enabled) {
		Role role= roleRepository.findByRoleName(roleName);
		Set<Role> roles= new HashSet<>();
		roles.add(role);
		
		return Login.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.roles(roles)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(enabled)
				.build();
	}
	
	public String encodePsw(String psw) {
		return passwordEncoder.encode(psw);
	}
	
	//convert appointment to AppointmentResponse
	public List<AppointmentResponse> toAptResponse(List<Appointment> appointments){
		List<AppointmentResponse> appointmentResponses= new ArrayList<>();
		
		for(Appointment a: appointments) {
			AppointmentResponse ar= new AppointmentResponse();
			ar.setTime(a.getAppointmentTime());
			ar.setAptAvl(a.getAppointmentAvailable());
			appointmentResponses.add(ar);
		}
		return appointmentResponses;
	}
	
	//convert BookedAppointments to BookedAppointmentResponse
	public List<BookedAppointmentResponse> toBookedAptResponse(List<BookedAppointment> bookedAppointments){
		List<BookedAppointmentResponse> appointmentResponses= new ArrayList<>();
		for(BookedAppointment ba: bookedAppointments) {
			BookedAppointmentResponse bar= new BookedAppointmentResponse();
			bar.setBookingId(ba.getBookingId());
			bar.setAptDate(ba.getAppointment().getAppointmentDate());
			bar.setAptTime(ba.getAppointment().getAppointmentTime());
			bar.setHospitalName(ba.getAppointment().getHospital().getHospitalName());
			appointmentResponses.add(bar);
		}
		return appointmentResponses;
	}
	
	private boolean isValidPassword(@NotNull String p1, @NotNull String p2) {
		return p1.equals(p2);
	}
}
