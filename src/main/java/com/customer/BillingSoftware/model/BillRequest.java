package com.customer.BillingSoftware.model;

import jakarta.validation.constraints.NotBlank;

public record BillRequest(
	@NotBlank String prompt
) {}
