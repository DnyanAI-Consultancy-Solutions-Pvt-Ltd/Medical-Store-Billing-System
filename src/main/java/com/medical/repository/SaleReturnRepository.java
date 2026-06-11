package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.SaleReturn;

public interface SaleReturnRepository
extends JpaRepository<SaleReturn,Long>{
}