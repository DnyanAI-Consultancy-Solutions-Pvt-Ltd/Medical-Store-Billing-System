package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medical.entity.PurchaseItem;

public interface PurchaseItemRepository
extends JpaRepository<PurchaseItem, Long> {

}