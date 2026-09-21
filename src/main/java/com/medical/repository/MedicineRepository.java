package com.medical.repository;

import com.medical.entity.Medicine;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	List<Medicine> findByQuantityLessThan(int i);
	List<Medicine> findByExpiryDateBetween(
            LocalDate startDate,
            LocalDate endDate);
	 // Search medicine by exact name
    Optional<Medicine> findByMedicineName(String medicineName);
}