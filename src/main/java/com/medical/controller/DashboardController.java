package com.medical.controller;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.repository.CustomerRepository;
import com.medical.repository.MedicineRepository;

@RestController
public class DashboardController {

    private final MedicineRepository medicineRepository;
    private final CustomerRepository customerRepository;

    public DashboardController(
            MedicineRepository medicineRepository,
            CustomerRepository customerRepository) {

        this.medicineRepository = medicineRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/api/dashboard")
    public Map<String,Object> getDashboard() {

        Map<String,Object> dashboard =
                new HashMap<>();

        dashboard.put(
                "totalMedicines",
                medicineRepository.count());

        dashboard.put(
                "totalCustomers",
                customerRepository.count());

        dashboard.put(
                "lowStock",
                medicineRepository
                        .findByQuantityLessThan(10)
                        .size());

        dashboard.put(
                "outOfStock",
                medicineRepository
                        .findByQuantityLessThan(1)
                        .size());

        dashboard.put(
                "expiringSoon",
                medicineRepository
                        .findByExpiryDateBetween(
                                LocalDate.now(),
                                LocalDate.now().plusDays(30))
                        .size());

        return dashboard;
    }
}