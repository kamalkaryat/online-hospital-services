package com.k2dev.hospital.api;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.PreUpdate;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
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
import com.k2dev.hospital.model.dto.Appointment;
import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.model.dto.HospitalAdmin;
import com.k2dev.hospital.model.dto.ProductQuantity;
import com.k2dev.hospital.model.dto.TestOrder;
import com.k2dev.hospital.model.request.DoctorSignupRequest;
import com.k2dev.hospital.model.request.ProductDto;
import com.k2dev.hospital.model.request.ProductStock;
import com.k2dev.hospital.model.request.ProfileUpdateRequest;
import com.k2dev.hospital.service.AccountService;
import com.k2dev.hospital.service.AppointmentService;
import com.k2dev.hospital.service.DoctorService;
import com.k2dev.hospital.service.HospitalAdminService;
import com.k2dev.hospital.service.HospitalService;
import static com.k2dev.hospital.util.Constants.*;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/v1/hospital-admins")
@AllArgsConstructor
public class HospitalAdminEndpoints {
	
	private final DoctorService doctorService;
	private final AppointmentService appointmentService;
	private final HospitalService hospitalService;
	private final HospitalAdminService hospitalAdminService;
	private final AccountService accountService;
	
	@PostMapping("/appointments")
	public ResponseEntity<?> addAppointmentRequestHandler(@Valid @RequestBody Appointment appointment){
		Appointment res= appointmentService.addAppointment(appointment);
		if(res!=null)
			return ResponseEntity.ok().body(res);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(APPOINTMENT_ADD_ERROR);
	}
	
	@DeleteMapping("/{id}/appointments/{appointmentId}")
	public ResponseEntity<?> deleteAppointmentRequestHandler(@PathVariable String appointmentId){
		boolean isDeleted= appointmentService.deleteAppointment(appointmentId);
		if(isDeleted)
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(APPOINTMENT_DELETE_ERROR);
	}
	
	@PutMapping("/{id}/appointments")
	public ResponseEntity<?> closeAllAppointmentRequestHandler(@RequestParam String hospitalId, 
			@RequestParam LocalDate date){
		System.out.println("hospitalId: "+hospitalId);
		boolean areAllClosed= appointmentService.closeAllAppointment(hospitalId, date);
		if(areAllClosed)
			return ResponseEntity.ok().body(ALL_APPOINTMENT_CLOSED);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ALL_APPOINTMENT_CLOSE_ERROR);
	}

	@PutMapping("/doctors/{username}")
	public ResponseEntity<String> updateAccountRequestHandler(@PathVariable String username,
			@RequestParam String action){
		boolean result= accountService.updateAccount(username, action);
		if(!result)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(USER_NOT_ENABLED);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/doctors")
	public ResponseEntity<?> addDoctor(@Valid @RequestBody DoctorSignupRequest request){
		boolean res= doctorService.saveDoctor(request);
		if(res)
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(DOCTOR_REGISTER_ERROR);		
	}
	
	@GetMapping("/doctors")
	public ResponseEntity<?> findDoctors(@RequestParam String id){
		List<Doctor> doctors= doctorService.findAllDoctorsInHospital(id);
		if(doctors==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(DOCTORS_NOT_FOUND);
		return ResponseEntity.ok().body(doctors);
	}
	@PostMapping("/products")
	public ResponseEntity<?> saveProductRequestHandler(@RequestBody ProductQuantity product){
		boolean res= hospitalService.addOrUpdateProductInHospital(product);
		if(!res)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PRODUCT_ADDorUPDATE_ERROR);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/products")
	public ResponseEntity<?> removeProductRequestHandler(@RequestParam String hId, 
			@RequestBody String productId)
			throws NotFoundException{
		boolean res= hospitalService.removeProductFromHospital(hId, productId);
		
		if(!res)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PRODUCT_REMOVE_ERROR);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> findProductQuantitiesRequestHandler(@RequestParam String id)
			throws NotFoundException{
		List<ProductQuantity> productQuantities= hospitalService.findProductQuantityInHospital(id);
	
		if(productQuantities==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
		return ResponseEntity.ok().body(productQuantities);
	}
	
	
	@PutMapping("/tests/{id}")
	public ResponseEntity<?> updateTestRequest(@PathVariable String id, @RequestParam String status){
		boolean isUpdated= hospitalAdminService.updateTestOrder(id, status);
		if(!isUpdated)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Status Not Updated");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/tests")
	public ResponseEntity<?> findAllTestRequests(@RequestParam String id){
		List<TestOrder> testOrders= hospitalAdminService.findTestOrders(id);
		if(testOrders==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test Orders Not Found");
		return ResponseEntity.ok().body(testOrders);
	}
	
	@GetMapping("/profile/{id}")
	public ResponseEntity<?> findHospitalAdminRequestHandler(@PathVariable String id){
		HospitalAdmin hospitalAdmin= hospitalAdminService.findHospitalAdmin(id);
		if(hospitalAdmin==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HOSPITAL_ADMIN_NOT_FOUND);
		return ResponseEntity.ok().body(hospitalAdmin);
	}
	
	@PutMapping("/profile")
	public ResponseEntity<?> updateHospitalAdminRequestHandler(@RequestBody ProfileUpdateRequest request){
		HospitalAdmin updatedHA= hospitalAdminService.updateHospitalAdmin(request);
		if(updatedHA==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(HOSPITAL_ADMIN_UPDATE_ERROR);
		return ResponseEntity.ok().body(updatedHA);
	}
	
}
