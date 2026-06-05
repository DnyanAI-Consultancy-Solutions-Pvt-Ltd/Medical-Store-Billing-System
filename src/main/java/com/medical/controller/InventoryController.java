package com.medical.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.entity.Medicine;
import com.medical.repository.MedicineRepository;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    public InventoryController(MedicineRepository medicineRepository) {
		super();
		this.medicineRepository = medicineRepository;
	}

	private final MedicineRepository medicineRepository;

    @GetMapping
    public List<Medicine> getAllStock() {
        return medicineRepository.findAll();
    }

    @GetMapping("/low-stock")
    public List<Medicine> getLowStockMedicines() {
        return medicineRepository.findByQuantityLessThan(10);
    }

    @GetMapping("/out-of-stock")
    public List<Medicine> getOutOfStockMedicines() {
        return medicineRepository.findByQuantityLessThan(1);
    }
    @GetMapping("/expiring-soon")
    public List<Medicine> getExpiringSoon() {

        LocalDate today = LocalDate.now();

        LocalDate next30Days =
                today.plusDays(30);

        return medicineRepository
                .findByExpiryDateBetween(
                        today,
                        next30Days);
    }
}