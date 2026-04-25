package com.customer.BillingSoftware.auth;

import com.customer.BillingSoftware.exception.InvalidCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final AuthProperties authProperties;
	private final JwtService jwtService;

	public AuthService(AuthProperties authProperties, JwtService jwtService) {
		this.authProperties = authProperties;
		this.jwtService = jwtService;
	}

	public LoginResponse login(LoginRequest request) {
		boolean emailMatches = authProperties.ownerEmail().equalsIgnoreCase(request.email().trim());
		boolean passwordMatches = authProperties.ownerPassword().equals(request.password());

		if (!emailMatches || !passwordMatches) {
			throw new InvalidCredentialsException("Invalid email or password.");
		}

		AuthenticatedUser user = getConfiguredUser();
		String token = jwtService.generateToken(user);
		return new LoginResponse(
			token,
			"Bearer",
			jwtService.getExpirationSeconds(),
			user
		);
	}

	public AuthenticatedUser getConfiguredUser() {
		return new AuthenticatedUser(
			authProperties.ownerEmail(),
			authProperties.ownerName(),
			authProperties.subscriptionPlan()
		);
	}
}
