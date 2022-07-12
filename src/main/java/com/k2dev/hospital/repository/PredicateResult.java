package com.k2dev.hospital.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.util.SearchCriteria;

/**
 * The <code>performOperation</code> method of this class is used by
 * all the implementor of <code>Specification<T></code>
 */
public class PredicateResult {
	
	/**
	 * This method will perform a operation based on the operation mentioned in 
	 * SearchCriteria object 
	 * @param root entity on which operation get executed
	 * @param query
	 * @param criteriaBuilder
	 * @param searchCriteria which contains attribute name, operation, constraint
	 * @return predicate after performing a specific operation
	 */
	public static Predicate performOperation(Root<Doctor> root, CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder, SearchCriteria searchCriteria) {
		Predicate predicate= null;
		switch (searchCriteria.getOperation()) {
		
			case ">":
				predicate= criteriaBuilder.greaterThan(root.<String>get(searchCriteria.getKey()), 
						searchCriteria.getValue().toString());
				break;
			
			case ">=":
				predicate= criteriaBuilder.greaterThanOrEqualTo(
			              root.<String> get(
			            		  searchCriteria.getKey()), searchCriteria.getValue().toString());
				break;
				
			case "<":
				predicate= criteriaBuilder.lessThan(root.<String>get(searchCriteria.getKey()), 
						searchCriteria.getValue().toString());
				break;
				
			case "<=":
				predicate= criteriaBuilder.lessThanOrEqualTo(
			              root.<String> get(
			            		  searchCriteria.getKey()), searchCriteria.getValue().toString());
				break;

			//for equality
			case ":":
				try {
	        		int val= Integer.parseInt(searchCriteria.getValue().toString());
	        		predicate= criteriaBuilder.equal(root.get(searchCriteria.getKey()), val);
	        	}catch (NumberFormatException e) {
	        		predicate= criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
				}
			case "!=":
				predicate= criteriaBuilder.notEqual(
			              root.<String> get(
			            		  searchCriteria.getKey()), searchCriteria.getValue().toString());
				break;
			
			default:
				break;
		}
		return predicate;
	}
}
