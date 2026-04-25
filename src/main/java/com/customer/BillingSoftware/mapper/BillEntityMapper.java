package com.customer.BillingSoftware.mapper;

import com.customer.BillingSoftware.dto.BillDetailsDto;
import com.customer.BillingSoftware.dto.BillLineItemDto;
import com.customer.BillingSoftware.dto.CustomerSummaryDto;
import com.customer.BillingSoftware.model.Bill;
import com.customer.BillingSoftware.persistence.entity.BillEntity;
import com.customer.BillingSoftware.persistence.entity.BillLineItemEntity;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class BillEntityMapper {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

	public Bill toBillSummary(BillEntity entity) {
		return new Bill(
			entity.getBillNumber(),
			entity.getCustomer().getFullName(),
			entity.getDescription(),
			entity.getTotalAmount().doubleValue(),
			entity.getBillDate().format(DATE_FORMATTER),
			entity.getStatus().name()
		);
	}

	public BillDetailsDto toBillDetails(BillEntity entity) {
		return new BillDetailsDto(
			entity.getBillNumber(),
			entity.getDescription(),
			entity.getBillDate() != null ? entity.getBillDate().format(DATE_FORMATTER) : null,
			entity.getDueDate() != null ? entity.getDueDate().format(DATE_FORMATTER) : null,
			entity.getStatus().name(),
			entity.getSubtotalAmount().doubleValue(),
			entity.getTaxAmount().doubleValue(),
			entity.getTotalAmount().doubleValue(),
			new CustomerSummaryDto(
				entity.getCustomer().getCustomerCode(),
				entity.getCustomer().getFullName(),
				entity.getCustomer().getPhoneNumber(),
				entity.getCustomer().getEmailAddress()
			),
			mapLineItems(entity.getLineItems()),
			entity.getNotes()
		);
	}

	private List<BillLineItemDto> mapLineItems(List<BillLineItemEntity> lineItems) {
		return lineItems.stream()
			.map(lineItem -> new BillLineItemDto(
				lineItem.getItemName(),
				lineItem.getItemDescription(),
				lineItem.getQuantity().doubleValue(),
				lineItem.getUnitPrice().doubleValue(),
				lineItem.getLineTotal().doubleValue()
			))
			.toList();
	}
}
