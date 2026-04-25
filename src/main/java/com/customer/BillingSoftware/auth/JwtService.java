package com.customer.BillingSoftware.auth;

import com.customer.BillingSoftware.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private final AuthProperties authProperties;
	private final SecretKey signingKey;

	public JwtService(AuthProperties authProperties) {
		this.authProperties = authProperties;
		this.signingKey = buildSigningKey(authProperties.jwtSecret());
	}

	public String generateToken(AuthenticatedUser user) {
		Instant now = Instant.now();
		Instant expiry = now.plusSeconds(getExpirationSeconds());

		return Jwts.builder()
			.subject(user.email())
			.claim("name", user.name())
			.claim("subscriptionPlan", user.subscriptionPlan())
			.issuedAt(Date.from(now))
			.expiration(Date.from(expiry))
			.signWith(signingKey)
			.compact();
	}

	public AuthenticatedUserContext parseToken(String token) {
		try {
			Claims claims = Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();

			return new AuthenticatedUserContext(
				claims.getSubject(),
				claims.get("name", String.class),
				claims.get("subscriptionPlan", String.class)
			);
		} catch (Exception exception) {
			throw new InvalidTokenException("JWT token is missing, expired, or invalid.");
		}
	}

	public long getExpirationSeconds() {
		return authProperties.jwtExpirationMinutes() * 60;
	}

	private SecretKey buildSigningKey(String secret) {
		try {
			byte[] decoded = Decoders.BASE64.decode(secret);
			return Keys.hmacShaKeyFor(decoded);
		} catch (IllegalArgumentException exception) {
			return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		}
	}
}
