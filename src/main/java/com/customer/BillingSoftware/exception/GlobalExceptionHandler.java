package com.customer.BillingSoftware.exception;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BillNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleBillNotFound(BillNotFoundException exception) {
		return build(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
	public ResponseEntity<ApiErrorResponse> handleValidation(Exception exception) {
		return build(HttpStatus.BAD_REQUEST, "Request validation failed.");
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidCredentials(InvalidCredentialsException exception) {
		return build(HttpStatus.UNAUTHORIZED, exception.getMessage());
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidToken(InvalidTokenException exception) {
		return build(HttpStatus.UNAUTHORIZED, exception.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGeneric(Exception exception) {
		return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error.");
	}

	private ResponseEntity<ApiErrorResponse> build(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(
			new ApiErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message)
		);
	}
}
