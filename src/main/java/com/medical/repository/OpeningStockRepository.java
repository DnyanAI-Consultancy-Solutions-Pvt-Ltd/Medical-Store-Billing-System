package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.OpeningStock;

public interface OpeningStockRepository
extends JpaRepository<OpeningStock, Long> {
}