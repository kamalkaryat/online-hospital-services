package com.k2dev.hospital.util;

import java.util.ArrayList;
import java.util.List;

import com.k2dev.hospital.model.dto.Appointment;
import com.k2dev.hospital.model.response.AppointmentDto;

public class AppointmentDtoConverter {
	
	public static List<AppointmentDto> appointmentToAppointmentDtoConverter(List<Appointment> appointments){
		List<AppointmentDto> appointmentDtos= new ArrayList<>();
		for(Appointment a: appointments) {
			AppointmentDto dto= new AppointmentDto();
			dto.setAppointmentId(a.getAppointmentId());
			dto.setAppointmentDate(a.getAppointmentDate());
			dto.setAppointmentTime(a.getAppointmentTime());
			dto.setAppointmentAvailable(a.getAppointmentAvailable());
			appointmentDtos.add(dto);
		}
		return appointmentDtos;
	}
}
