package com.customer.BillingSoftware.controller;

import com.customer.BillingSoftware.model.AssistantResponse;
import com.customer.BillingSoftware.model.Bill;
import com.customer.BillingSoftware.model.BillRequest;
import com.customer.BillingSoftware.model.BillSearchResult;
import com.customer.BillingSoftware.exception.BillNotFoundException;
import com.customer.BillingSoftware.service.BillingOperations;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class BillingController {

	private final BillingOperations billingService;

	public BillingController(BillingOperations billingService) {
		this.billingService = billingService;
	}

	@GetMapping("/health")
	public BillSearchResult health() {
		return new BillSearchResult("ok", "Billing API is running");
	}

	@GetMapping("/bills")
	public List<Bill> getBills() {
		return billingService.findAll();
	}

	@GetMapping("/bills/{id}")
	public ResponseEntity<Bill> getBillById(@PathVariable String id) {
		Bill bill = billingService.findById(id)
			.orElseThrow(() -> new BillNotFoundException(id));
		return ResponseEntity.status(HttpStatus.OK).body(bill);
	}

	@GetMapping("/bills/search")
	public List<Bill> searchBills(@RequestParam(defaultValue = "") String q) {
		return billingService.search(q);
	}

	@PostMapping("/bills/parse")
	public Bill parseBill(@Validated @RequestBody BillRequest request) {
		return billingService.parsePrompt(request.prompt());
	}

	@PostMapping("/bills")
	public ResponseEntity<Bill> createBill(@Validated @RequestBody BillRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createFromPrompt(request.prompt()));
	}

	@PostMapping("/assistant/command")
	public AssistantResponse handleCommand(@Validated @RequestBody BillRequest request) {
		return billingService.handlePrompt(request.prompt());
	}
}
