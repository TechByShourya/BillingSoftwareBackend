package com.customer.BillingSoftware.service;

import com.customer.BillingSoftware.model.ParsedBillDraft;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class BillPromptParser {

	private static final Pattern AMOUNT_PATTERN = Pattern.compile("(?:Rs\\.?|INR)?\\s?(\\d[\\d,]*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern CUSTOMER_PATTERN = Pattern.compile(
		"(?:for|bill for|invoice for|fetch the old bill for)\\s+(.+?)(?:\\s+(?:from|on|worth|amount|of)\\b|,|$)",
		Pattern.CASE_INSENSITIVE
	);
	private static final Pattern DESCRIPTION_PATTERN = Pattern.compile(
		"(?:for\\s+.+?\\s+for\\s+|for\\s+|about\\s+|regarding\\s+)(.+?)(?:\\s+(?:worth|amount|from|on)\\b|,|$)",
		Pattern.CASE_INSENSITIVE
	);
	private static final Pattern SEARCH_HINT_PATTERN = Pattern.compile(
		"\\b(fetch|find|search|show|look up|lookup|older|previous|history)\\b",
		Pattern.CASE_INSENSITIVE
	);

	public ParsedBillDraft parseDraft(String prompt) {
		String safePrompt = prompt == null ? "" : prompt.trim();
		Matcher amountMatcher = AMOUNT_PATTERN.matcher(safePrompt);
		double amount = amountMatcher.find() ? parseAmount(amountMatcher.group(1)) : 0;
		Matcher customerMatcher = CUSTOMER_PATTERN.matcher(safePrompt);
		String customer = customerMatcher.find() ? customerMatcher.group(1).trim() : "New Customer";
		Matcher descriptionMatcher = DESCRIPTION_PATTERN.matcher(safePrompt);
		String description = descriptionMatcher.find() ? descriptionMatcher.group(1).trim() : "Custom billing request";
		return new ParsedBillDraft(customer, description, amount);
	}

	public boolean isSearchPrompt(String prompt) {
		String safePrompt = prompt == null ? "" : prompt.trim().toLowerCase(Locale.ENGLISH);
		return SEARCH_HINT_PATTERN.matcher(safePrompt).find();
	}

	private double parseAmount(String value) {
		return Double.parseDouble(value.replace(",", ""));
	}
}
