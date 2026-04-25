package com.customer.BillingSoftware.controller;

import com.customer.BillingSoftware.auth.AuthService;
import com.customer.BillingSoftware.auth.AuthenticatedUserContext;
import com.customer.BillingSoftware.auth.LoginRequest;
import com.customer.BillingSoftware.auth.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@GetMapping("/me")
	public ResponseEntity<AuthenticatedUserContext> me(HttpServletRequest request) {
		AuthenticatedUserContext authenticatedUser =
			(AuthenticatedUserContext) request.getAttribute("authenticatedUser");
		return ResponseEntity.ok(authenticatedUser);
	}
}
