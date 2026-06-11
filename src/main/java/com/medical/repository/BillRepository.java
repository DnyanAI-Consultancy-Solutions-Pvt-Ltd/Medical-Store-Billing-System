package com.medical.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.*;
public interface BillRepository
extends JpaRepository<Bill, Long> {
	List<Bill> findTop10ByOrderByBillDateDesc();
}