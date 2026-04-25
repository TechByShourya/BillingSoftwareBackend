package com.customer.BillingSoftware.exception;

import java.time.LocalDateTime;

public record ApiErrorResponse(
	LocalDateTime timestamp,
	int status,
	String error,
	String message
) {}
