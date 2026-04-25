package com.customer.BillingSoftware.persistence.repository;

import com.customer.BillingSoftware.persistence.entity.BillEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<BillEntity, Long> {

	Optional<BillEntity> findByBillNumberIgnoreCase(String billNumber);

	List<BillEntity> findByBillNumberContainingIgnoreCaseOrCustomer_FullNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
		String billNumber,
		String customerName,
		String description
	);
}
