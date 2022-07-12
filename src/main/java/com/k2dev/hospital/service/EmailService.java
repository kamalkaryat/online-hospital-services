package com.k2dev.hospital.service;

import com.k2dev.hospital.util.EmailDetails;

public interface EmailService {
	/**
	 * Used for sending an email
	 * @param emailDetails contains email details
	 * @return a code
	 */
	int sendMail(EmailDetails emailDetails);
}
