package com.mentale.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<?> handleUserExists(UserAlreadyExistsException e) {
		return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException e) {
		return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError err : e.getBindingResult().getFieldErrors()) {
			errors.put(err.getField(), err.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(errors);
	}
}
