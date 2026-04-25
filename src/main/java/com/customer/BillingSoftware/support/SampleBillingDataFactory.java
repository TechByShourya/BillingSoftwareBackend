package com.customer.BillingSoftware.support;

import com.customer.BillingSoftware.model.Bill;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SampleBillingDataFactory {

	public List<Bill> createBills() {
		return List.of(
			new Bill("INV-2048", "Aarav Sharma", "AC servicing and filter replacement", 4800, "18 Apr 2026", "Paid"),
			new Bill("INV-2047", "Meera Iyer", "Quarterly maintenance visit", 2600, "15 Apr 2026", "Pending"),
			new Bill("INV-2046", "Sanjay Patel", "Spare parts and labour", 9125, "11 Apr 2026", "Paid")
		);
	}
}
