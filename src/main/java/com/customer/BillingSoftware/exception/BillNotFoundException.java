package com.customer.BillingSoftware.exception;

public class BillNotFoundException extends RuntimeException {

	public BillNotFoundException(String billId) {
		super("Bill not found: " + billId);
	}
}
