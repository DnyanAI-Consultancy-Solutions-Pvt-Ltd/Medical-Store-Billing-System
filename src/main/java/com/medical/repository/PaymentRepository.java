package com.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.Payment;

public interface PaymentRepository
extends JpaRepository<Payment,Long>{
	 List<Payment> findBySupplierId(Long supplierId);
}