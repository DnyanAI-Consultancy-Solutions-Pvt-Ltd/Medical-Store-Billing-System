package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medical.entity.PurchaseReturn;

public interface PurchaseReturnRepository
extends JpaRepository<PurchaseReturn,Long>{
	@Query("""
			select coalesce(sum(p.quantity),0)
			from PurchaseReturn p
			where p.medicine.id=:medicineId
			""")
			Integer getReturnQty(Long medicineId);
}