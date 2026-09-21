package com.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medical.entity.Supplier;

public interface SupplierRepository
extends JpaRepository<Supplier, Long> {

}