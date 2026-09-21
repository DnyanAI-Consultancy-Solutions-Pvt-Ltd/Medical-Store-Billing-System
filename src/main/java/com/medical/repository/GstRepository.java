package com.medical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.GstMaster;

public interface GstRepository extends JpaRepository<GstMaster, Long> {

    Optional<GstMaster> findByMedicine_MedicineName(String medicineName);
    boolean existsByMedicine_MedicineName(String medicineName);    
}