package com.k2dev.hospital.model.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Table(name= "areas")
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Area {
	
	@Column(nullable = false)
	private String areaName;
	
	@Column(nullable = false)
	private int pincode;
	
	@Column(nullable = false)
	private String district;
	
	@Column(nullable = false)
	private String state;
	
	@Id
	private int areaId;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
//	@JsonBackReference
//	private List<Hospital> hospitals;

//	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
//	@JsonBackReference
//	private List<Patient> patients;
//	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
//	@JsonBackReference
//	private List<Lab> labs;
}
