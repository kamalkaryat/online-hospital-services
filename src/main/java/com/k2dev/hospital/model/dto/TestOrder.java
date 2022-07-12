package com.k2dev.hospital.model.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.k2dev.hospital.util.TestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name= "test_orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TestOrder {
	
	@Id
	private String orderId;
	
	@ManyToOne
	@JoinColumn(name= "patient_id")
	private Patient patient;
	
	@OneToOne
	@JoinColumn(name= "product_id")
	private Product product;
	
	@Column(nullable = false)
	private LocalDateTime orderDateTime;
	
	@Column(nullable = false)
	private TestStatus status;
	
	@OneToOne
	@JoinColumn(name= "lab_id")
	private Lab lab;
}
