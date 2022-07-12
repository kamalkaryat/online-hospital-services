package com.k2dev.hospital.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.util.SearchCriteria;

/**
 * JPA will use it to find records from database.
 * Filter data based on some constraints
 */
public class DoctorSpecification implements Specification<Doctor> {

	private static final long serialVersionUID = 5182323152468891449L;
	private final SearchCriteria searchCriteria;
	
	public DoctorSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria= searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return PredicateResult.performOperation(root, query, criteriaBuilder, searchCriteria);
	}
	
}
