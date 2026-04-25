package com.customer.BillingSoftware.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bill_line_items")
public class BillLineItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "bill_id", nullable = false)
	private BillEntity bill;

	@Column(name = "item_name", nullable = false, length = 120)
	private String itemName;

	@Column(name = "item_description", length = 255)
	private String itemDescription;

	@Column(name = "quantity", nullable = false, precision = 10, scale = 2)
	private BigDecimal quantity = BigDecimal.ONE;

	@Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
	private BigDecimal unitPrice = BigDecimal.ZERO;

	@Column(name = "line_total", nullable = false, precision = 12, scale = 2)
	private BigDecimal lineTotal = BigDecimal.ZERO;

	protected BillLineItemEntity() {
	}

	public Long getId() {
		return id;
	}

	public BillEntity getBill() {
		return bill;
	}

	public void setBill(BillEntity bill) {
		this.bill = bill;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
}
