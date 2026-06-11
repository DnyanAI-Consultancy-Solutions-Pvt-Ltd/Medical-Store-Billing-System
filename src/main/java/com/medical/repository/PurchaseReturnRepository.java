package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.PurchaseReturn;

public interface PurchaseReturnRepository
extends JpaRepository<PurchaseReturn,Long>{
}