package com.k2dev.hospital.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Admin{
	@Id
	private String id; 
	
	private String name;
	
	@OneToOne
	@JoinColumn(name = "username")
	@NotNull
	private Login login;
}
