package com.k2dev.hospital.api;

import java.security.Principal;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.k2dev.hospital.model.dto.Patient;
import com.k2dev.hospital.model.dto.ProductPurchased;
import com.k2dev.hospital.model.dto.TestOrderResponse;
import com.k2dev.hospital.model.request.AppointmentRescheduleRequest;
import com.k2dev.hospital.model.request.PatientSignupRequest;
import com.k2dev.hospital.model.request.ProfileUpdateRequest;
import com.k2dev.hospital.model.request.PurchaseProductRequest;
import com.k2dev.hospital.model.request.TestRequest;
import com.k2dev.hospital.model.response.BookedAppointmentResponse;
import com.k2dev.hospital.repository.PatientRepository;
import com.k2dev.hospital.model.request.AppointmentRequest;
import com.k2dev.hospital.service.AppointmentService;
import com.k2dev.hospital.service.PatientService;
import com.k2dev.hospital.service.ProductService;

import lombok.AllArgsConstructor;

import static com.k2dev.hospital.util.Constants.TEST_ORDER_ERROR;
import static com.k2dev.hospital.util.Constants.PRODUCT_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.PRODUCT_NOT_PURCHASED;
import static com.k2dev.hospital.util.Constants.APPOINTMENT_RESHCEDULE_ERROR;
import static com.k2dev.hospital.util.Constants.APPOINTMENT_RESHCEDULED;
import static com.k2dev.hospital.util.Constants.APPOINTMENT_NOT_CANCELLED;
import static com.k2dev.hospital.util.Constants.APPOINTMENT_CANCELLED;
import static com.k2dev.hospital.util.Constants.PATIENT_REGISTRATION_ERROR;
import static com.k2dev.hospital.util.Constants.PATIENT_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.APPOINTMENT_BOOKING_ERROR;
import static com.k2dev.hospital.util.Constants.PATIENT_REGISTERED;
import static com.k2dev.hospital.util.Constants.PATIENT_UPDATE_ERROR;
import static com.k2dev.hospital.util.Constants.APPOINTMENT_NOT_FOUND;

@RestController
@RequestMapping("/v1/patients")
@AllArgsConstructor
public class PatientEndpoints {

	private final AppointmentService appointmentService;
	private final PatientService patientService;
	private final ProductService productService;
	private final PatientRepository patientRepository;
	
	@PostMapping("/")
	public ResponseEntity<String> savePatientRequestHandler(@RequestBody PatientSignupRequest request){
		boolean isRegisterd= patientService.savePatient(request);
		if(isRegisterd)
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PATIENT_REGISTRATION_ERROR);
	}
	
	@GetMapping("/currentPatient")
	public ResponseEntity<?> findCurrentPatientRequestHandler(Principal principal){
		String username= principal.getName();
		Patient patient= patientRepository.findByLoginUsername(username);
		if(patient==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PATIENT_NOT_FOUND);
		return ResponseEntity.ok().body(patient);
	}
	
	@PutMapping("/profile")
	public ResponseEntity<?> updatePatientRequestHandler(@RequestBody ProfileUpdateRequest request){
		Patient patient= patientService.updateProfile(request);
		if(patient!=null)
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PATIENT_UPDATE_ERROR);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findPatientRequestHandler(@PathVariable String id){
		Patient patient= patientService.findPatient(id);
		if(patient!=null)
			return ResponseEntity.ok().body(patient);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PATIENT_NOT_FOUND);
	}
	
	@PostMapping("/{patientId}/appointments")
	public ResponseEntity<?> bookAppointmentRequestHandler(@RequestBody AppointmentRequest request){
		String bookingId= appointmentService.bookAppointment(request);
		if(bookingId!=null)
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(APPOINTMENT_BOOKING_ERROR);
	}
	
	@DeleteMapping("/{patientId}/appointments/{bookingId}")
	public ResponseEntity<?> cancelAppointmentRequestHandler(@PathVariable String bookingId){
		boolean isCanceled= appointmentService.cancelAppointment(bookingId);
		if(isCanceled)
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(APPOINTMENT_NOT_CANCELLED);
	}
	
	@PutMapping("/{patientId}/appointments")
	public ResponseEntity<?> rescheduleAppointmentRequestHandler(@RequestBody AppointmentRequest
			request){
		boolean isRescheduled= appointmentService.rescheduleAppointment(request);
		if(isRescheduled)
			return ResponseEntity.ok().build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(APPOINTMENT_RESHCEDULE_ERROR);
	}
	
	@GetMapping("/{patientId}/appointments")
	public ResponseEntity<?> findBookedAppointmentsRequestHandler(@PathVariable String patientId){
		List<BookedAppointmentResponse> bar= appointmentService.findBookedAppointments(patientId);
		if(bar==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(APPOINTMENT_NOT_FOUND);
		return ResponseEntity.ok().body(bar);
	}
	
	@GetMapping("/tests")
	public ResponseEntity<?> findPatientTestsRequestHandler(@RequestParam String id)
			throws NotFoundException{
		List<TestOrderResponse> testOrders= patientService.findPatientTestRequests(id);
		return ResponseEntity.ok().body(testOrders);
	}
	
	@PostMapping("/tests")
	public ResponseEntity<?> placeTestOrderRequestHandler(@RequestBody TestRequest testRequest)
			throws NotFoundException{
		String orderId= patientService.placeTestRequest(testRequest);
		if(orderId==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(TEST_ORDER_ERROR);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> purchaseProductRequestHandler(
			@RequestBody PurchaseProductRequest request) throws Exception{
		String txnId= productService.purchaseProduct(request);
		if(txnId==null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(PRODUCT_NOT_PURCHASED);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{patientId}/products")
	public ResponseEntity<?> findPurchasedProductsRequestHandler(@PathVariable String patientId){
		List<ProductPurchased> purchasedProducts= productService.findProductPurchased(patientId);
		if(purchasedProducts==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
		return ResponseEntity.ok().body(purchasedProducts);
	}
}
