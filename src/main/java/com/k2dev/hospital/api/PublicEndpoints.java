package com.k2dev.hospital.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.k2dev.hospital.model.dto.Appointment;
import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Lab;
import com.k2dev.hospital.model.dto.Product;
import com.k2dev.hospital.model.dto.ProductQuantity;
import com.k2dev.hospital.model.response.AppointmentDto;
import com.k2dev.hospital.model.response.AppointmentResponse;
import com.k2dev.hospital.service.AppointmentService;
import com.k2dev.hospital.service.DoctorService;
import com.k2dev.hospital.service.HospitalService;
import com.k2dev.hospital.service.LabService;
import com.k2dev.hospital.service.ProductService;
import com.k2dev.hospital.service.ProductStockService;

import lombok.AllArgsConstructor;

import static com.k2dev.hospital.util.Constants.PRODUCT_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.DOCTORS_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.HOSPITALS_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.APPOINTMENT_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.LAB_NOT_FOUND;
import static com.k2dev.hospital.util.Constants.PRODUCT_STOCK_NOT_FOUND;

@RestController
@RequestMapping("/v1/public")
@AllArgsConstructor
public class PublicEndpoints {

	private final DoctorService doctorService;
	private final HospitalService hospitalService;
	private final LabService labService;
	private final AppointmentService appointmentService;
	private final ProductService productService;
	private final ProductStockService productStockService;
	
	@GetMapping("/doctors")
	public ResponseEntity<?> findDoctorsRequestHandler(@RequestParam String filters){
		
		List<Doctor> doctors= doctorService.findDoctorsByFilter(filters);
		if(doctors==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DOCTORS_NOT_FOUND);
		return ResponseEntity.ok().body(doctors);
	}
	
	@GetMapping("/hospitals")
	public ResponseEntity<?> findHospitalsRequestHandler(){
		List<Hospital> hospitals= hospitalService.findAllActiveHospitals();
		if(hospitals!=null)
			return ResponseEntity.ok().body(hospitals);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HOSPITALS_NOT_FOUND);
	}
	
	@GetMapping("/hospitals/appointments")
	public ResponseEntity<?> findAppointmentRequestHandler(@RequestParam String name,
						@RequestParam String date){
		List<AppointmentResponse> appointments= appointmentService
				.findAppointment(name, LocalDate.parse(date));
		if(appointments!=null)
			return ResponseEntity.ok().body(appointments);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APPOINTMENT_NOT_FOUND);
	}
	
	@GetMapping("/hospitals/{id}/appointments")
	public ResponseEntity<?> findAllAppointmentsInHospitalRequestHandler(@PathVariable String id){
		List<Appointment> appointments= appointmentService.findAppointmentsInHospital(id);
		if(appointments!=null)
			return ResponseEntity.ok().body(appointments);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APPOINTMENT_NOT_FOUND);
	}
	
	@GetMapping("/labs")
	public ResponseEntity<?> findLabRequestHandler(){
		List<Lab> hospitals= labService.findAllActiveLabs();
		if(hospitals!=null)
			return ResponseEntity.ok().body(hospitals);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LAB_NOT_FOUND);
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> findProductsRequestHandler(){
		List<Product> products= productService.findProducts();
		if(products==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
		return ResponseEntity.ok().body(products);
	}
	
	@GetMapping("/product")
	public ResponseEntity<?> findProductsRequestHandler(@RequestParam String productId){
		Product product= productService.findProduct(productId);
		if(product==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
		return ResponseEntity.ok().body(product);
	}
	
	@GetMapping("/hospital/product/stock")
	public ResponseEntity<?> find(@RequestParam String hospitalId, @RequestParam String productId){
		ProductQuantity pq= productStockService.findProductStockInHospital(hospitalId, productId);
		if(pq==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_STOCK_NOT_FOUND);
		return ResponseEntity.ok().body(pq);
	}
	
	@GetMapping("/hospital/products/stock")
	public ResponseEntity<?> findProductsInHospital(@RequestParam String hospitalId){
		List<ProductQuantity> pqs= productStockService.findProductsStockInHospital(hospitalId);
		if(pqs==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_STOCK_NOT_FOUND);
		return ResponseEntity.ok().body(pqs)    ;
	}
	
	@GetMapping("hospitals/products/stock")
	public ResponseEntity<?> findProductQuantity(@RequestParam("category") String category, 
			@RequestParam("name") String name){
		
		List<ProductQuantity> pqs= productStockService.findProductStocks(category, name);
		
		if(pqs==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_STOCK_NOT_FOUND);
		return ResponseEntity.ok().body(pqs);
	}
	
	@GetMapping("/products/category")
	public ResponseEntity<?> findProductCategoryRequestHandler(){
		List<String> categoryList= productService.findProductCategory();
		if(categoryList==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Found");
		return ResponseEntity.ok().body(categoryList);
	}
	
	@GetMapping("/products/category/{category}")
	public ResponseEntity<?> findProductsByCategoryRequestHandler(@PathVariable String category){
		List<String> productList= productService.findProductNames(category);
		if(productList==null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Products Not Found");
		return ResponseEntity.ok().body(productList);
	}
	
}
