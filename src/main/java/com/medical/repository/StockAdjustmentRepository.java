package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.StockAdjustment;


public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment,Long>{

}
