package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medical.entity.SaleReturn;

public interface SaleReturnRepository
extends JpaRepository<SaleReturn,Long>{
	@Query("""
			select coalesce(sum(s.quantity),0)
			from SaleReturn s
			where s.medicine.id=:medicineId
			""")
			Integer getReturnQty(Long medicineId);
}