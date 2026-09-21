package com.medical.repository;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medical.entity.*;
public interface BillRepository
extends JpaRepository<Bill, Long> {
	List<Bill> findTop10ByOrderByBillDateDesc();
	 long countByBillDateBetween(LocalDateTime start, LocalDateTime end);
	 @Query("""
			 SELECT b FROM Bill b
			 WHERE (:customer='' OR LOWER(b.customer.customerName)
			 LIKE LOWER(CONCAT('%',:customer,'%')))
			 AND (:type='' OR b.billType=:type)
			 """)
			 List<Bill> searchBills(
			     @Param("customer") String customer,
			     @Param("type") String type
			 );
	  List<Bill> findByCustomer_Id(Long customerId);
	 
}