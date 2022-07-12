package com.k2dev.hospital.util;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchCriteria {
	private String key;
	private String operation;
	private Object value;
}
