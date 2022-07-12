package com.k2dev.hospital.exception;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 3259197621814453535L;
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
}
