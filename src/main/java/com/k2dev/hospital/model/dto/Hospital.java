package com.k2dev.hospital.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.k2dev.hospital.util.Constants.*;
import com.k2dev.hospital.util.BusinessType;
import com.k2dev.hospital.util.PhoneNo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name= "hospitals")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Hospital {
	
	@Id
	private String hospitalId;
	
	@Column(nullable = false)
	@Size(min= 5, max = 30, message = HOSPITAL_NAME_ERROR)
	private String hospitalName;
	
	@Column(nullable = false)
	private BusinessType hospitalType;
	
	@PhoneNo
	@Column(nullable = false)
	private long hospitalPhoneNo;
	
	@Column(columnDefinition = "boolean default false")
	private boolean hospitalStatus;
	
	@Email
	private String hospitalEmail;

	@ManyToOne
	@JoinColumn(name = "area_id")
	@NotNull
	private Area area;

}
