package com.k2dev.hospital.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.k2dev.hospital.model.dto.Lab;

public interface LabService {
	/**
	 * Find labs based on user's filter
	 * @param filters filters
	 * @return filtered list of labs
	 */
	List<Lab> findLabs(String filters);
	
	/**
	 * Find a lab based on its id
	 * @param labId id of a lab
	 * @return lab
	 */
	Lab findLab(String labId);
	
	/**
	 * Register a lab
	 * @param lab lab
	 * @return true on success
	 */
	boolean saveLab(Lab lab);
	
	/**
	 * Update a lab
	 * @param lab lab object
	 * @return return updated lab
	 */
	Lab updateLab(Lab lab);
	
	/**
	 * Find only active labs
	 * @return list of labs
	 */
	List<Lab> findAllActiveLabs();
	
	/**
	 * Find all the labs either active or inactive
	 * @return list of labs
	 */
	List<Lab> findAllLabs();
	
	/**
	 * Enable or disable lab
	 * @param labId id of lab
	 * @param action enable/disable
	 * @return true on success
	 */
	boolean manageLab(String labId, String action);
}
