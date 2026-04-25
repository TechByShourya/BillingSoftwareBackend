package com.customer.BillingSoftware.dto;

import java.util.List;

public record BillDetailsDto(
	String billNumber,
	String description,
	String billDate,
	String dueDate,
	String status,
	double subtotalAmount,
	double taxAmount,
	double totalAmount,
	CustomerSummaryDto customer,
	List<BillLineItemDto> lineItems,
	String notes
) {}
