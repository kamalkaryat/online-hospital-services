package com.k2dev.hospital.util;

import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.exception.UserNotFoundException;
import com.k2dev.hospital.model.dto.Admin;
import com.k2dev.hospital.model.dto.Appointment;
import com.k2dev.hospital.model.dto.Area;
import com.k2dev.hospital.model.dto.BookedAppointment;
import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.HospitalAdmin;
import com.k2dev.hospital.model.dto.Lab;
import com.k2dev.hospital.model.dto.Patient;
import com.k2dev.hospital.model.response.User;
import com.k2dev.hospital.repository.AdminRepository;
import com.k2dev.hospital.repository.AreaRepository;
import com.k2dev.hospital.repository.BookedAppointmentRepository;
import com.k2dev.hospital.repository.DoctorRepository;
import com.k2dev.hospital.repository.HospitalAdminRepository;
import com.k2dev.hospital.repository.HospitalRepository;
import com.k2dev.hospital.repository.LabRepository;
import com.k2dev.hospital.repository.LoginRepository;
import com.k2dev.hospital.repository.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ObjectFinder {
	private final PatientRepository patientRepository;
	private final HospitalAdminRepository hospitalAdminRepository;
	private final BookedAppointmentRepository bookedAppointmentRepository;
	private final AreaRepository areaRepository;
	private final HospitalRepository hospitalRepository;
	private final LabRepository labRepository;
	private final AdminRepository adminRepository;
	private final DoctorRepository doctorRepository;
	
	//find patient
	public Patient findPatient(String patientId) {
		return patientRepository.findById(patientId).orElseThrow(
				()-> new ObjectNotFoundException("Patient not found with id: "+patientId));
	}

	//find hospital-admin
	public HospitalAdmin findHospitalAdmin(String hospitalAdminId) {
		return hospitalAdminRepository.findById(hospitalAdminId).orElseThrow(
				()-> new ObjectNotFoundException("HospitalAdmin not found with id: "+hospitalAdminId));
	}
	
	//find appointment
	public Appointment findAppointmentByBookingId(String bookingId) {
		BookedAppointment ba= bookedAppointmentRepository.findById(bookingId)
				.orElseThrow(
					()-> new ObjectNotFoundException("Booked Appointment Not Found with id: "+bookingId));
		return ba.getAppointment();
	}

	//find area
	public int findAreaId(int pincode, String areaName) {
		Area area= areaRepository.findByPincodeAndAreaName(pincode, areaName);
		if(area==null)
			throw new ObjectNotFoundException("area not found");
		return area.getAreaId();
	}
	
	//find hospital
	public Hospital findHospital(Hospital request) {
		Hospital h= hospitalRepository.findByHospitalName(request.getHospitalName());
		if(h==null)
			throw new ObjectNotFoundException("hospital not found");
		return h;
	}

	public Lab findLab(String labName, String hospitaName) {
		Lab lab= labRepository.findByLabNameAndHospitalHospitalName(labName, hospitaName);
		if(lab==null)
			throw new ObjectNotFoundException("lab not found");
		return lab;
	}
	
	public User findUser(String username, String role) {
		User user= new User();

		switch (role) {
		case "PATIENT":
			Patient patient= patientRepository.findByLoginUsername(username);
			if(patient==null)
				throw new ObjectNotFoundException("Patient not found with id: "+username);
			
			user.setId(patient.getPatientId());
			user.setArea(patient.getArea());
			user.setEnabled(true);
			break;
		
		case "HOSPITAL_ADMIN":
			HospitalAdmin ha= hospitalAdminRepository.findByLoginUsername(username);
			if(ha==null)
				throw new ObjectNotFoundException("HospitalAdmin not found with id: "+username);
			
			user.setId(ha.getHospitalAdminId());
			user.setEnabled(ha.getLogin().isEnabled());
			user.setHospital(ha.getHospital());
			break;
			
		case "DOCTOR":
			Doctor doctor= doctorRepository.findByLoginUsername(username);
			if(doctor==null)
				throw new ObjectNotFoundException("Doctor not found with id: "+username);
			
			user.setId(doctor.getDoctorId());
			user.setEnabled(doctor.getLogin().isEnabled());
			user.setHospital(doctor.getHospital());
			break;
			
		case "ADMIN":
			Admin admin= adminRepository.findByLoginUsername(username);
			if(admin==null)
				throw new ObjectNotFoundException("Admin not found with id: "+username);
			
			user.setId(admin.getId());
			user.setEnabled(admin.getLogin().isEnabled());
			break;
		
			default:
		}
		return user;
	}
	
}
