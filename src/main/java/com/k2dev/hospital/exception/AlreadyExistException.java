package com.k2dev.hospital.exception;

/**
 * Report if objects area already exists
 * @author kamal
 */
public class AlreadyExistException extends RuntimeException{
	 
	private static final long serialVersionUID = 8700584255443628590L;

	public AlreadyExistException(String message) {
		super(message);
	}
}
