package com.k2dev.hospital.service;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.k2dev.hospital.exception.AlreadyExistException;
import com.k2dev.hospital.exception.ObjectNotFoundException;
import com.k2dev.hospital.model.dto.Area;
import com.k2dev.hospital.model.dto.Hospital;
import com.k2dev.hospital.model.dto.Lab;
import com.k2dev.hospital.repository.AreaRepository;
import com.k2dev.hospital.repository.LabRepository;
import com.k2dev.hospital.util.ObjectFinder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LabServiceImp implements LabService {

	private final LabRepository labRepository;
	private final ObjectFinder objectFinder;
	
	@Override
	public List<Lab> findLabs(String filters) {
		return labRepository.findAll();
	}

	@Override
	public Lab findLab(String labId){
		return labRepository.findById(labId).orElseThrow(
				()-> new ObjectNotFoundException("Lab not found with id: "+labId));
	}

	@Override
	public boolean saveLab(@NotNull Lab lab) {
		
		Hospital hospital= lab.getHospital();

		Hospital validHospital= objectFinder.findHospital(hospital);
		if(validHospital==null)
			throw new IllegalArgumentException("Invalid Hospital");
		
		lab.setHospital(validHospital);
		
		boolean alreadyExists= labRepository.
				existsByHospitalHospitalIdAndLabName(validHospital.getHospitalId(), lab.getLabName());
		if(alreadyExists)
			throw new AlreadyExistException("Lab already exists");
		
		lab.setLabId(UUID.randomUUID().toString());
		return labRepository.save(lab)!=null;
	}

	@Override
	public Lab updateLab(Lab lab) {
		return labRepository.save(lab);
	}

	@Override
	public List<Lab> findAllActiveLabs() {
		return labRepository.findAllByHospitalHospitalStatusTrueAndLabStatusTrue();
	}

	@Override
	public List<Lab> findAllLabs() {
		return labRepository.findAll();
	}

	@Override
	public boolean manageLab(String labId, String action) {
		Lab lab= labRepository.findById(labId).orElseThrow(
				()-> new ObjectNotFoundException("Lab not found with id: "+labId));
		
		boolean status= true;
		
		if(!action.equalsIgnoreCase("enable"))
			status= false;
		
		lab.setLabStatus(status);
		
		Lab updatedLab= labRepository.save(lab);
		return updatedLab.isLabStatus() == status;
	} 

}
