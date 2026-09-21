package com.medical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.entity.ScheduleMaster;

public interface ScheduleRepository
extends JpaRepository<ScheduleMaster,Long>{

	Optional<ScheduleMaster> findByMedicine_MedicineName(String medicineName);
	ScheduleMaster findByMedicineId(Long medicineId);
	boolean existsByMedicine_MedicineName(String medicineName);

}