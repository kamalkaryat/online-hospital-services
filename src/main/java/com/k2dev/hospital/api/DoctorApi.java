package com.k2dev.hospital.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.service.DoctorService;
import static com.k2dev.hospital.util.Constants.DOCTOR_UPDATE_ERROR;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorApi {

	private final DoctorService doctorService;
	
	@Autowired
	public DoctorApi(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateDoctor(@RequestBody Doctor doctor){
		Doctor doc= doctorService.updateDoctor(doctor);
		if(doc==null)
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(DOCTOR_UPDATE_ERROR);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(doc);
	}
	
}
