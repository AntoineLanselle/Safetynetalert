package com.safetynet.safetynetalert.exception;

public class RessourceNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Construit une RessourceNotFoundException.
	 * 
	 * @param le message de l'exception.
	 * 
	 */
	public RessourceNotFoundException(String message) {
		super(message);
	}

}
