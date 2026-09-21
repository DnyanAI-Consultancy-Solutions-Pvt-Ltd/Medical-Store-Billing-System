package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medical.entity.Purchase;

public interface PurchaseRepository
        extends JpaRepository<Purchase, Long> {
}