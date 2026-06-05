package com.medical.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.*;
public interface BillRepository
extends JpaRepository<Bill, Long> {
}