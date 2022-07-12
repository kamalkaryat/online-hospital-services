package com.k2dev.hospital.model.dto;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.k2dev.hospital.util.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;

@Entity
@Table(name= "hospital_admins")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class HospitalAdmin {

	@Id
	private String hospitalAdminId;
	
	@Column(nullable = false)
	private String hospitalAdminName;
	
	private LocalDate hospitalAdminDob;
	
	private Gender hospitalAdminGender;
	
	@Column(nullable = false)
	private long hospitalAdminPhoneNo;
	
	@OneToOne
	@JoinColumn(name= "hospital_id")
	private Hospital hospital;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username")
	private Login login;
}
