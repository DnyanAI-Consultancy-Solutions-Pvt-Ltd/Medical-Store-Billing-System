package com.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medical.entity.BillItem;

public interface BillItemRepository
extends JpaRepository<BillItem, Long> {
	@Query("""
			select coalesce(sum(b.quantity),0)
			from BillItem b
			where b.medicine.id=:medicineId
			""")
			Integer getSaleQty(Long medicineId);
	List<BillItem> findByBillId(Long billId);
}