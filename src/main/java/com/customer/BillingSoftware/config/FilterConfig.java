package com.customer.BillingSoftware.config;

import com.customer.BillingSoftware.auth.JwtAuthenticationFilter;
import com.customer.BillingSoftware.exception.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

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
				writeUnauthorizedResponse((HttpServletResponse) response, objectMapper, exception.getMessage());
			}
		});
		registrationBean.addUrlPatterns("/v1/api/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		return registrationBean;
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
