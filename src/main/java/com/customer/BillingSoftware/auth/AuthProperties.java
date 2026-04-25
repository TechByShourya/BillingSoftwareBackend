package com.customer.BillingSoftware.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.auth")
public record AuthProperties(
	String ownerEmail,
	String ownerPassword,
	String ownerName,
	String subscriptionPlan,
	String jwtSecret,
	long jwtExpirationMinutes
) {
}
