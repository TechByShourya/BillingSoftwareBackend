package com.customer.BillingSoftware.auth;

public record AuthenticatedUser(
	String email,
	String name,
	String subscriptionPlan
) {
}
