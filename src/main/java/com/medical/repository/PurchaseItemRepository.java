package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medical.entity.PurchaseItem;

public interface PurchaseItemRepository
extends JpaRepository<PurchaseItem, Long> {
	@Query("""
			select coalesce(sum(p.quantity),0)
			from PurchaseItem p
			where p.medicine.id=:medicineId
			""")
			Integer getPurchaseQty(Long medicineId);
}