package com.medical.repository;

import com.medical.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByCustomerName(String customerName);
}