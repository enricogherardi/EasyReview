package it.lea.exceptions;

public class RegistrationException extends Exception {
	private static final long serialVersionUID = 1L;

	public RegistrationException(String message) {
		super(message);
	}
}