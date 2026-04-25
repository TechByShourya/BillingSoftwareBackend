package com.customer.BillingSoftware.auth;

public record AuthenticatedUserContext(
	String email,
	String name,
	String subscriptionPlan
) {
}
