package com.customer.BillingSoftware.persistence.repository;

import com.customer.BillingSoftware.persistence.entity.BillLineItemEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillLineItemRepository extends JpaRepository<BillLineItemEntity, Long> {

	List<BillLineItemEntity> findByBill_Id(Long billId);
}
