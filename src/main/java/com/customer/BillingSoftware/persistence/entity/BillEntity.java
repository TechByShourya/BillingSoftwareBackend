package com.customer.BillingSoftware.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
public class BillEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "bill_number", nullable = false, unique = true, length = 40)
	private String billNumber;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerEntity customer;

	@Column(name = "bill_date", nullable = false)
	private LocalDate billDate;

	@Column(name = "due_date")
	private LocalDate dueDate;

	@Column(name = "description", nullable = false, length = 255)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private BillStatus status;

	@Column(name = "subtotal_amount", nullable = false, precision = 12, scale = 2)
	private BigDecimal subtotalAmount = BigDecimal.ZERO;

	@Column(name = "tax_amount", nullable = false, precision = 12, scale = 2)
	private BigDecimal taxAmount = BigDecimal.ZERO;

	@Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
	private BigDecimal totalAmount = BigDecimal.ZERO;

	@Column(name = "notes", length = 500)
	private String notes;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<BillLineItemEntity> lineItems = new ArrayList<>();

	protected BillEntity() {
	}

	public Long getId() {
		return id;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public LocalDate getBillDate() {
		return billDate;
	}

	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}

	public BigDecimal getSubtotalAmount() {
		return subtotalAmount;
	}

	public void setSubtotalAmount(BigDecimal subtotalAmount) {
		this.subtotalAmount = subtotalAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public List<BillLineItemEntity> getLineItems() {
		return lineItems;
	}
}
