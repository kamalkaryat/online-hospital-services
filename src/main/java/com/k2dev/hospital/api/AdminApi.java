package com.k2dev.hospital.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.HospitalAdmin;
import com.k2dev.hospital.model.dto.Lab;
import com.k2dev.hospital.model.dto.Patient;
import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.request.HospitalAdminSignupRequest;
import com.k2dev.hospital.service.AccountService;
import com.k2dev.hospital.service.AdminService;
import com.k2dev.hospital.service.DoctorService;
import com.k2dev.hospital.service.HospitalAdminService;
import com.k2dev.hospital.service.HospitalService;
import com.k2dev.hospital.service.LabService;
import com.k2dev.hospital.service.PatientService;

import lombok.AllArgsConstructor;

import static com.k2dev.hospital.util.Constants.USER_ENABLED;
import static com.k2dev.hospital.util.Constants.USER_NOT_DISABLED;
import static com.k2dev.hospital.util.Constants.USER_NOT_ENABLED;
import static com.k2dev.hospital.util.Constants.USER_DISABLED;
import static com.k2dev.hospital.util.Constants.NEW_HOSPITAL_ERROR;
import static com.k2dev.hospital.util.Constants.PATIENT_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.PRODUCT_REGISTER_ERROR;
import static com.k2dev.hospital.util.Constants.PRODUCT_REMOVE_ERROR;
import static com.k2dev.hospital.util.Constants.PRODUCT_UPDATE_ERROR;
import static com.k2dev.hospital.util.Constants.HOSPITAL_ADMIN_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.HOSPITAL_ADMIN_REGISTER_ERROR;
import static com.k2dev.hospital.util.Constants.LAB_UPDATE_ERROR;
import static com.k2dev.hospital.util.Constants.LAB_REGISTER_ERROR;
import static com.k2dev.hospital.util.Constants.LAB_ACTION_ERROR;
import static com.k2dev.hospital.util.Constants.HOSPITALS_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.DOCTORS_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.LAB_NOT_FOUND;;

@RestController
@RequestMapping("/v1/admin")
@AllArgsConstructor
public class AdminApi {
	
	private final HospitalService hospitalService;
	private final PatientService patientService;
	private final HospitalAdminService hospitalAdminService;
	private final LabService labService;
	private final AccountService accountService;
	private final AdminService adminService;
	private final DoctorService doctorService;
	
	@PostMapping("/hospitals")
	public ResponseEntity<?> addHospitalRequestHandler(@Valid @RequestBody Hospital hospitals){
		boolean isRegistered= hospitalService.saveHospital(hospitals);
		if(!isRegistered)	
			return ResponseEntity.status(HttpStatus.CONFLICT).body(NEW_HOSPITAL_ERROR);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/hospitals")
	public ResponseEntity<?> findHospitalsRequestHandler(){
		List<Hospital> hospitals= hospitalService.findAllHospitals();
		if(hospitals==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HOSPITALS_NOT_FOUND);
		return ResponseEntity.ok().body(hospitals);
	}
	
	@GetMapping("/doctors")
	public ResponseEntity<?> findDoctorsRequestHandler(){
		List<Doctor> doctors= doctorService.findAllDoctors();
		if(doctors==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DOCTORS_NOT_FOUND);
		return ResponseEntity.ok().body(doctors);
	}
	
	@GetMapping("/labs")
	public ResponseEntity<?> findLabsRequetsHandler(){
		List<Lab> labs= labService.findAllLabs();
		if(labs==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LAB_NOT_FOUND);
		return ResponseEntity.ok().body(labs);
	}

	@PutMapping("/hospitals/{id}")
	public ResponseEntity<?> disableHospitalsRequestHandler(@PathVariable String id ,
			@RequestParam String action){
		boolean res= hospitalService.manageHospital(id, action);
		if(res)
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Error in enabling/disabling hospital");
	}
	
	@GetMapping("/patients")
	public ResponseEntity<?> findPatientsRequestHandler(){
		List<Patient> patients= patientService.findAllPatients();
		if(patients!=null)
			return ResponseEntity.ok().body(patients);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PATIENT_NOT_FOUND);
	}
	
	@PostMapping("/hospital-admins")
	public ResponseEntity<?> saveHospitalAdminRequestHandler(
			@Valid @RequestBody HospitalAdminSignupRequest signupRequest){
		
		boolean isRegistered= adminService.addHospitalAdmin(signupRequest);
		if(!isRegistered)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HOSPITAL_ADMIN_REGISTER_ERROR);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/hospital-admins")
	public ResponseEntity<?> findHospitalAdminsRequestHandler(){
		List<HospitalAdmin> hospitalAdmins= hospitalAdminService.findHospitalAdmins();
		if(hospitalAdmins==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HOSPITAL_ADMIN_NOT_FOUND);
		return ResponseEntity.ok().body(hospitalAdmins);
	}
	
	@GetMapping("/hospital-admins/{id}")
	public ResponseEntity<?> findHospitalAdminRequestHandler(@PathVariable String id){
		HospitalAdmin hospitalAdmin= hospitalAdminService.findHospitalAdmin(id);
		if(hospitalAdmin==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HOSPITAL_ADMIN_NOT_FOUND);
		return ResponseEntity.ok().body(hospitalAdmin);
	}
	
	@PutMapping("labs/{id}")
	public ResponseEntity<?> manageLabRequestHandler(@PathVariable String id,
			@RequestParam String action){
		boolean res= labService.manageLab(id, action);
		if(!res)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(LAB_ACTION_ERROR);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("labs")
	public ResponseEntity<String> addLabRequestHandler(@Valid @RequestBody Lab lab){
		boolean isRegistered= this.labService.saveLab(lab);
		if(!isRegistered)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(LAB_REGISTER_ERROR);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/labs")
	public ResponseEntity<?> updateLabRequestHandler(@RequestBody Lab lab){
		Lab updatedLab= this.labService.updateLab(lab);
		if(updatedLab==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(LAB_UPDATE_ERROR);
		return ResponseEntity.ok().body(updatedLab);
	}
	
	@PutMapping("/users/{username}")
	public ResponseEntity<String> updateAccountRequestHandler(@PathVariable String username,
			@RequestParam String action){
		boolean result= accountService.updateAccount(username, action);
		if(!result)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(USER_NOT_ENABLED);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> saveProductRequestHandler(@Valid @RequestBody Product product){
		Product p= adminService.addProduct(product);
		if(p==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PRODUCT_REGISTER_ERROR);
		return ResponseEntity.ok().body(p);
	}
	
	@PutMapping("/products")
	public ResponseEntity<?> updateProductRequestHandler(@Valid @RequestBody Product product){
		Product p= adminService.updateProduct(product);
		if(p==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PRODUCT_UPDATE_ERROR);
		return ResponseEntity.ok().body(p);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> removeProductRequestHandler(@PathVariable String id)
			throws NotFoundException{
		boolean isRemoved= adminService.deleteProduct(id);
		
		if(!isRemoved)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PRODUCT_REMOVE_ERROR);
		return ResponseEntity.ok().body("Products removed");
	}
}
