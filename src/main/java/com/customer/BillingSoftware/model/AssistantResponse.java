package com.customer.BillingSoftware.model;

import java.util.List;

public record AssistantResponse(
	String action,
	String message,
	Bill bill,
	List<Bill> bills
) {}
