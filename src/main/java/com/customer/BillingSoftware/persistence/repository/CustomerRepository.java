package com.customer.BillingSoftware.persistence.repository;

import com.customer.BillingSoftware.persistence.entity.CustomerEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	Optional<CustomerEntity> findByCustomerCodeIgnoreCase(String customerCode);

	Optional<CustomerEntity> findByFullNameIgnoreCase(String fullName);

	List<CustomerEntity> findByFullNameContainingIgnoreCase(String fullName);
}
