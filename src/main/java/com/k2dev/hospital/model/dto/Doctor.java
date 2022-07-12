package com.k2dev.hospital.model.dto;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.k2dev.hospital.util.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name= "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Doctor {
	@Id
	private String doctorId;
	
	@Column(nullable = false)
	private String doctorName;
	
	private long doctorPhoneNo;
	
	@Column(nullable = false)
	private Gender doctorGender;
	
	private LocalDate doctorDob;
	
	@Column(nullable = false)
	private String doctorQualification;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean doctorStatus;
	
	@Column(nullable = false)
	private String doctorDept;
	
	@ManyToOne
	@JoinColumn(name= "hospital_id")
	private Hospital hospital;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username")
	private Login login;
	
}
