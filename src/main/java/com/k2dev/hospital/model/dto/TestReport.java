package com.k2dev.hospital.model.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name= "test_reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestReport {

	@Id
	private String testReportId;
	
	@Column(nullable = false)
	private LocalDate testReportDate;
	
	@Column(nullable = false)
	private byte[] testReportInfo;
	
	@OneToOne
	@JoinColumn(name= "orderId")
	private TestOrder testOrder;
}
