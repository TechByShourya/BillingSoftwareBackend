package com.customer.BillingSoftware.model;

public record Bill(
	String id,
	String customer,
	String description,
	double amount,
	String date,
	String status
) {}
