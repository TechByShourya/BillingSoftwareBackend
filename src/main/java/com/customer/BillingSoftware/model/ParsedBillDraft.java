package com.customer.BillingSoftware.model;

public record ParsedBillDraft(
	String customer,
	String description,
	double amount
) {}
