package com.k2dev.hospital.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "appointments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {
	
	@Id
	private String appointmentId;
	
	@Column(nullable = false)
	@NotNull
	private LocalDate appointmentDate;
	
	@Column(nullable = false)
	@NotNull
	private LocalTime appointmentTime;
	
	@Column(nullable = false)
	private int appointmentAvailable;
	
	@Column(nullable= false)
	@Range(min = 10, max = 100, message = "Total Appointments Values must lies between 10 to 100")
	private Integer totalAppointments;
	
	@ManyToOne
	@JoinColumn(name = "hospital_id")
	@NotNull
	private Hospital hospital;
}
