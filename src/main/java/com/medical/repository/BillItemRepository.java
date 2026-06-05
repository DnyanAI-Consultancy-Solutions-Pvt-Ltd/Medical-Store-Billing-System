package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.BillItem;

public interface BillItemRepository
extends JpaRepository<BillItem, Long> {
}