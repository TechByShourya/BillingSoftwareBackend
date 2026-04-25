package com.customer.BillingSoftware.dto;

public record BillLineItemDto(
	String itemName,
	String itemDescription,
	double quantity,
	double unitPrice,
	double lineTotal
) {}
