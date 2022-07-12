package com.k2dev.hospital.model.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "test_samples")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestSample {
	@Id
	private String testSampleId;
	
	@Column(nullable = false)
	private LocalDateTime collectionDate;
	private LocalDateTime receivedDate;
	
	@ManyToOne
	@JoinColumn(name= "patient_id")
	private Patient patient;
	
	@OneToOne
	@JoinColumn(name= "lab_id")
	private Lab lab;
}
