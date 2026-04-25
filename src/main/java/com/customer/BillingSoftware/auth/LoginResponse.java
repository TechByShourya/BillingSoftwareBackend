package com.customer.BillingSoftware.auth;

public record LoginResponse(
	String token,
	String tokenType,
	long expiresInSeconds,
	AuthenticatedUser user
) {
}
