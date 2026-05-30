package com.customer.BillingSoftware.auth;

import com.customer.BillingSoftware.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Set<String> PUBLIC_PATHS = Set.of(
		"/v1/api/health",
		"/v1/api/auth/login"
	);

	private final JwtService jwtService;
	private final AuthService authService;

	public JwtAuthenticationFilter(JwtService jwtService, AuthService authService) {
		this.jwtService = jwtService;
		this.authService = authService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String path = request.getRequestURI();

		if (PUBLIC_PATHS.contains(path) || "OPTIONS".equalsIgnoreCase(request.getMethod())) {
			filterChain.doFilter(request, response);
			return;
		}

		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			throw new InvalidTokenException("Authorization header with Bearer token is required.");
		}

		String token = header.substring(7).trim();
		AuthenticatedUserContext authenticatedUser = jwtService.parseToken(token);
		AuthenticatedUser configuredUser = authService.getConfiguredUser();

		if (!configuredUser.email().equalsIgnoreCase(authenticatedUser.email())) {
			throw new InvalidTokenException("JWT token does not belong to the subscribed account.");
		}

		request.setAttribute("authenticatedUser", authenticatedUser);
		filterChain.doFilter(request, response);
	}
}
