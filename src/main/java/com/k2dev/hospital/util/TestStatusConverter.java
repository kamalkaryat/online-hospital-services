package com.k2dev.hospital.util;

public class TestStatusConverter {
	public TestStatus convert(String status) {
		TestStatus ts = null;
		switch(status.toUpperCase()) {
			case "REQUEST_ACCEPTED":
				ts= TestStatus.REQUEST_ACCEPTED;
				break;
			
			case "REQUEST_REJECTED":
				ts= TestStatus.REQUEST_REJECTED;
				break;
				
			case "SAMPLE_RECEIVED":
				ts= TestStatus.SAMPLE_RECEIVED;
				break;
				
			case "SAMPLE_COLLECTED":
				ts= TestStatus.SAMPLE_COLLECTED;
				break;
				
			case "RESULT_PENDING":
				ts= TestStatus.RESULT_PENDING;
				break;
				
			case "REPORT_UPLOADED":
				ts= TestStatus.REPORT_UPLOADED;
				break;
				
			case "TRANSFERED_TO_OTHER_LAB":
				ts= TestStatus.TRANSFERED_TO_OTHER_LAB;
				break;
		}
		return ts;
	}
}
