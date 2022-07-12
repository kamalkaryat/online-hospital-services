package com.k2dev.hospital.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.k2dev.hospital.util.BusinessType;
import com.k2dev.hospital.util.PhoneNo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name ="labs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Lab {
	@Id
	private String labId;
	
	@Column(nullable = false)
	private String labName;
	
	@Column(columnDefinition = "boolean default false")
	private boolean labStatus;
	
	@ManyToOne
	@JoinColumn(name= "hospital_id")
	@NotNull
	private Hospital hospital;
}
