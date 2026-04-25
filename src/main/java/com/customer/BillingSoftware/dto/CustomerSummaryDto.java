package com.customer.BillingSoftware.dto;

public record CustomerSummaryDto(
	String customerCode,
	String fullName,
	String phoneNumber,
	String emailAddress
) {}
