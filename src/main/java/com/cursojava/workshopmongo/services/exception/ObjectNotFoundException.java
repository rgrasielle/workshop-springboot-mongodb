package com.cursojava.workshopmongo.services.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// Sobrepor o construtor básico
	public ObjectNotFoundException(String msg) {
		super(msg);
	}

}
