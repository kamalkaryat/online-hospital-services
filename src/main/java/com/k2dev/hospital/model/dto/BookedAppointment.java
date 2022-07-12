package com.k2dev.hospital.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.NonFinal;

@Entity
@Table(name= "booked_appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookedAppointment {
	
	@Id
	private String bookingId;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "appointment_id")
	private Appointment appointment;
}
