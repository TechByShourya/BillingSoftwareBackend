package com.customer.BillingSoftware.service;

import com.customer.BillingSoftware.model.AssistantResponse;
import com.customer.BillingSoftware.model.Bill;
import com.customer.BillingSoftware.model.ParsedBillDraft;
import com.customer.BillingSoftware.support.SampleBillingDataFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

@Service
public class BillingService implements BillingOperations {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
	private final BillPromptParser billPromptParser;
	private final List<Bill> bills;

	public BillingService(BillPromptParser billPromptParser, SampleBillingDataFactory sampleBillingDataFactory) {
		this.billPromptParser = billPromptParser;
		this.bills = new CopyOnWriteArrayList<>(sampleBillingDataFactory.createBills());
	}

	@Override
	public List<Bill> findAll() {
		return List.copyOf(bills);
	}

	@Override
	public Optional<Bill> findById(String id) {
		return bills.stream()
			.filter(bill -> bill.id().equalsIgnoreCase(id))
			.findFirst();
	}

	@Override
	public List<Bill> search(String query) {
		if (query == null || query.isBlank()) {
			return findAll();
		}

		String normalized = query.toLowerCase(Locale.ENGLISH);
		return bills.stream()
			.filter(bill -> matches(normalized, bill))
			.toList();
	}

	@Override
	public Bill parsePrompt(String prompt) {
		return buildDraft(prompt, "Draft", false);
	}

	@Override
	public Bill createFromPrompt(String prompt) {
		Bill bill = buildDraft(prompt, "Pending", true);
		bills.add(0, bill);
		return bill;
	}

	@Override
	public AssistantResponse handlePrompt(String prompt) {
		String safePrompt = prompt == null ? "" : prompt.trim();
		boolean searchRequest = billPromptParser.isSearchPrompt(safePrompt);

		if (searchRequest) {
			List<Bill> matches = search(safePrompt);
			return new AssistantResponse(
				"search",
				matches.isEmpty()
					? "No matching older bills were found."
					: "Found " + matches.size() + " matching bill(s).",
				null,
				matches
			);
		}

		Bill createdBill = createFromPrompt(safePrompt);
		return new AssistantResponse(
			"create",
			"Bill created and added to the ledger.",
			createdBill,
			findAll()
		);
	}

	private Bill buildDraft(String prompt, String status, boolean persistId) {
		ParsedBillDraft parsedBillDraft = billPromptParser.parseDraft(prompt);

		String billId = persistId
			? "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ENGLISH)
			: "INV-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase(Locale.ENGLISH);

		return new Bill(
			billId,
			parsedBillDraft.customer(),
			parsedBillDraft.description(),
			parsedBillDraft.amount(),
			LocalDate.now().format(DATE_FORMATTER),
			status
		);
	}

	private boolean matches(String query, Bill bill) {
		List<String> fields = new ArrayList<>();
		fields.add(bill.id());
		fields.add(bill.customer());
		fields.add(bill.description());
		fields.add(bill.date());
		fields.add(bill.status());
		return fields.stream().anyMatch(field -> field != null && field.toLowerCase(Locale.ENGLISH).contains(query));
	}
}
