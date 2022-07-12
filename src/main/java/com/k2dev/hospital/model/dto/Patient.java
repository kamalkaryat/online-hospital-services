package com.k2dev.hospital.model.dto;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.k2dev.hospital.util.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name= "patients")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Patient {
	@Id
	private String patientId;
	
	@Column(nullable = false)
	private String patientName;
	
	@Column(nullable = false)
	private LocalDate patientDob;
	
	@Column(nullable = false)
	private Gender patientGender;
	
	@Column(nullable = false)
	private long patientPhoneNo;
	
	@ManyToOne
	@JoinColumn(name= "area_id")
	private Area area;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username")
	private Login login;

	public String getUsername() {
		if(login==null)
			return null;
		return login.getUsername();
	}
	
	
}
