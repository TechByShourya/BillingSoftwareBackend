package com.customer.BillingSoftware.config;

import com.customer.BillingSoftware.auth.AuthService;
import com.customer.BillingSoftware.auth.JwtAuthenticationFilter;
import com.customer.BillingSoftware.auth.JwtService;
import com.customer.BillingSoftware.exception.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

	private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
		"http://localhost:5173",
		"https://billing-software-frontend-nine.vercel.app"
	);

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, AuthService authService) {
		return new JwtAuthenticationFilter(jwtService, authService);
	}

	@Bean
	public FilterRegistrationBean<Filter> jwtFilterRegistration(
		JwtAuthenticationFilter jwtAuthenticationFilter,
		ObjectMapper objectMapper
	) {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter((request, response, chain) -> {
			try {
				jwtAuthenticationFilter.doFilter(request, response, chain);
			} catch (RuntimeException exception) {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				addCorsHeaders(httpRequest, httpResponse);
				writeUnauthorizedResponse(httpResponse, objectMapper, exception.getMessage());
			}
		});
		registrationBean.addUrlPatterns("/v1/api/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		return registrationBean;
	}

	private void addCorsHeaders(HttpServletRequest request, HttpServletResponse response) {
		String origin = request.getHeader("Origin");
		if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
			response.setHeader("Access-Control-Allow-Origin", origin);
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "*");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Expose-Headers", "Authorization");
		}
	}

	private void writeUnauthorizedResponse(
		HttpServletResponse response,
		ObjectMapper objectMapper,
		String message
	) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		objectMapper.writeValue(
			response.getWriter(),
			new ApiErrorResponse(LocalDateTime.now(), 401, "Unauthorized", message)
		);
	}
}

