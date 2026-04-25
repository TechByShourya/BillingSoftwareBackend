package com.customer.BillingSoftware.service;

import com.customer.BillingSoftware.model.AssistantResponse;
import com.customer.BillingSoftware.model.Bill;
import java.util.List;
import java.util.Optional;

public interface BillingOperations {

	List<Bill> findAll();

	Optional<Bill> findById(String id);

	List<Bill> search(String query);

	Bill parsePrompt(String prompt);

	Bill createFromPrompt(String prompt);

	AssistantResponse handlePrompt(String prompt);
}
