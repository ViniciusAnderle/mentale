package com.mentale.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException(String message) {
		super("Esse usuário já existe");
	}
}
