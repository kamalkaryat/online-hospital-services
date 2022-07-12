package com.k2dev.hospital.util;

import java.util.ArrayList;
import java.util.List;

import com.k2dev.hospital.model.dto.TestOrder;
import com.k2dev.hospital.model.dto.TestOrderResponse;

public class TestOrderResponseMapper {
	private static List<TestOrderResponse> list= new ArrayList<>();
	
	public static List<TestOrderResponse> convert(List<TestOrder> testOrders) {
		for(TestOrder to: testOrders) {
			TestOrderResponse response= TestOrderResponse.builder()
					.labId(to.getLab().getLabId())
					.orderDateTime(to.getOrderDateTime())
					.orderId(to.getOrderId())
					.patientId(to.getPatient().getPatientId())
					.status(to.getStatus())
					.build();
			list.add(response);
		}	
		return list;
	}
	
}
