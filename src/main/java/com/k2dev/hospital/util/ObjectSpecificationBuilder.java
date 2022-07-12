package com.k2dev.hospital.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.k2dev.hospital.model.dto.Doctor;
import com.k2dev.hospital.repository.DoctorSpecification;

public class ObjectSpecificationBuilder {
    
    private List<SearchCriteria> params;
  
    public ObjectSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public ObjectSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

	public Specification<Doctor> build() {
        if (params.size() == 0) {
            return null;
        }
		List<Specification> specs = params.stream()
          .map(DoctorSpecification::new)
          .collect(Collectors.toList());
        
        @SuppressWarnings("rawtypes")
		Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
//        	check AND / OR predicate in search query
//            result = params.get(i)
//            		.isOrPredicate() ? 
//            		 Specification.where(result).or(specs.get(i))
//            		 : Specification.where(result).and(specs.get(i));
        	result= Specification.where(result).and(specs.get(i));		//by default AND predicate is used here 
        }       
        return result;
    }
}
